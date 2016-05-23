package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.Public;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Eval is a monad which controls evaluation.
 * <p>
 * This type wraps a value (or a computation that produces a value)
 * and can produce it on command via the `.value()` method.
 * <p>
 * There are three basic evaluation strategies:
 * <p>
 * - Now:    evaluated immediately
 * - Later:  evaluated once when value is needed
 * - Always: evaluated every time value is needed
 * <p>
 * The Later and Always are both lazy strategies while Now is eager.
 * Later and Always are distinguished from each other only by
 * memoization: once evaluated Later will save the value to be returned
 * immediately if it is needed again. Always will run its computation
 * every time.
 * <p>
 * Eval supports stack-safe lazy computation via the .map and .flatMap
 * methods, which use an internal trampoline to avoid stack overflows.
 * Computation done within .map and .flatMap is always done lazily,
 * even when applied to a Now instance.
 * <p>
 * Use .map and .flatMap to chain computation, and use .value
 * to get the result when needed. It is not good style to create
 * Eval instances whose computation involves calling .value on another
 * Eval instance -- this can defeat the trampolining and lead to stack
 * overflows.
 */
@Public
public interface Eval<A> extends Serializable {
  static <A> Eval<A> now(A a) {
    return new Now<>(a);
  }

  @Public
  static <A> Eval<A> later(Supplier<A> a) {
    return new Later<>(a);
  }

  @Public
  static <A> Eval<A> always(Supplier<A> a) {
    return new Always<>(a);
  }

  /**
   * Evaluate the computation and return an A value.
   * <p>
   * For lazy instances (Later, Always), any necessary computation
   * will be performed at this point. For eager instances (Now), a
   * value will be immediately returned.
   */
  A value();

  /**
   * Transform an Eval&lt;A&gt; into an Eval&lt;B&gt; given the transformation
   * function `f`.
   * <p>
   * This call is stack-safe -- many .map calls may be chained without
   * consumed additional stack during evaluation.
   * <p>
   * Computation performed in f is always lazy, even when called on an
   * eager (Now) instance.
   */
  @Public
  default <B> Eval<B> map(Function<A, B> f) {
    return flatMap((A a) -> now(f.apply(a)));
  }

  /**
   * Lazily perform a computation based on an Eval&lt;A&gt;, using the
   * function `f` to produce an Eval&lt;B&gt; given an A.
   * <p>
   * This call is stack-safe -- many .flatMap calls may be chained
   * without consumed additional stack during evaluation. It is also
   * written to avoid left-association problems, so that repeated
   * calls to .flatMap will be efficiently applied.
   * <p>
   * Computation performed in f is always lazy, even when called on an
   * eager (Now) instance.
   */
  default <B> Eval<B> flatMap(Function<A, Eval<B>> f) {
    return new Compute<>(() -> this, f);
  }

  /**
   * Ensure that the result of the computation (if any) will be
   * memoized.
   * <p>
   * Practically, this means that when called on an Always&lt;A&gt; a
   * Later&lt;A&gt; with an equivalent computation will be returned.
   */
  @Public
  Eval<A> memoize();
}

/**
 * Construct an eager Eval&lt;A&gt; instance.
 * <p>
 * In some sense it is equivalent to using a val.
 * <p>
 * This type should be used when an A value is already in hand, or
 * when the computation to produce an A value is pure and very fast.
 */
final class Now<A> implements Eval<A> {
  private final A value;

  Now(A value) {
    this.value = value;
  }

  @Override
  public A value() {
    return value;
  }

  @Override
  public Eval<A> memoize() {
    return this;
  }
}

/**
 * Construct a lazy Eval&lt;A&gt; instance.
 * <p>
 * This type should be used for most "lazy" values. In some sense it
 * is equivalent to using a lazy val.
 * <p>
 * When caching is not required or desired (e.g. if the value produced
 * may be large) prefer Always. When there is no computation
 * necessary, prefer Now.
 * <p>
 * Once Later has been evaluated, the closure (and any values captured
 * by the closure) will not be retained, and will be available for
 * garbage collection.
 */
final class Later<A> implements Eval<A> {
  private Supplier<A> thunk;
  private Option<A> value = Option.none();

  Later(Supplier<A> thunk) {
    this.thunk = thunk;
  }

  @Override
  public A value() {
    if (!value.isDefined()) {
      value = Option.some(thunk.get());
      thunk = null;
    }
    return value.get();
  }

  @Override
  public Eval<A> memoize() {
    return this;
  }
}

/**
 * Construct a lazy Eval&lt;A&gt; instance.
 * <p>
 * This type can be used for "lazy" values. In some sense it is
 * equivalent to using a Supplier value.
 * <p>
 * This type will evaluate the computation every time the value is
 * required. It should be avoided except when laziness is required and
 * caching must be avoided. Generally, prefer Later.
 */
final class Always<A> implements Eval<A> {
  private final Supplier<A> f;

  Always(Supplier<A> f) {
    this.f = f;
  }

  @Override
  public A value() {
    return f.get();
  }

  @Override
  public Eval<A> memoize() {
    return new Later<>(f);
  }
}

/**
 * Compute is a type of Eval&lt;A&gt; that is used to chain computations
 * involving .map and .flatMap. Along with Eval#flatMap it
 * implements the trampoline that guarantees stack-safety.
 * <p>
 * Users should not instantiate Compute instances
 * themselves. Instead, they will be automatically created when
 * needed.
 * <p>
 * Unlike a traditional trampoline, the internal workings of the
 * trampoline are not exposed. This allows a slightly more efficient
 * implementation of the .value method.
 */
class Compute<A> implements Eval<A> {
  private final Supplier<Eval> start;
  private final Function run;

  Compute(Supplier<Eval> start, Function run) {
    this.start = start;
    this.run = run;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <B> Eval<B> flatMap(Function<A, Eval<B>> f) {
    return new Compute<>(start,
        s -> new Compute<>(() -> (Eval)this.run.apply(s), f));
  }

  @Override
  public Eval<A> memoize() {
    return new Later<>(this::value);
  }

  @SuppressWarnings("unchecked")
  public A value() {
    LinkedList<Function> fs = new LinkedList<>();
    Eval curr = this;
    boolean cont = true;

    while (cont) {
      if (curr instanceof Compute) {
        Compute c = (Compute) curr;
        Eval cstart = (Eval) c.start.get();
        if (cstart instanceof Compute) {
          fs.add(0, c.run);
          curr = cstart;
        } else {
          curr = (Eval) c.run.apply(cstart.value());
        }
      } else {
        if (fs.isEmpty()) {
          cont = false;
        } else {
          Function f = fs.pop();
          curr = (Eval) f.apply(curr.value());
        }
      }
    }
    return (A) curr.value();
  }
}