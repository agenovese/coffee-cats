package ca.genovese.coffeecats.data.eval;

import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.kind.Kind;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Eval is a monad which controls evaluation.
 *
 * <p>This type wraps a value (or a computation that produces a value)
 * and can produce it on command via the `.value()` method.
 *
 * <p>There are three basic evaluation strategies:
 * <ul>
 * <li>Now:    evaluated immediately</li>
 * <li>- Later:  evaluated once when value is needed</li>
 * <li>- Always: evaluated every time value is needed</li>
 * </ul>
 *
 * <p>The Later and Always are both lazy strategies while Now is eager.
 * Later and Always are distinguished from each other only by
 * memoization: once evaluated Later will save the value to be returned
 * immediately if it is needed again. Always will run its computation
 * every time.
 *
 * <p>Eval supports stack-safe lazy computation via the .map and .flatMap
 * methods, which use an internal trampoline to avoid stack overflows.
 * Computation done within .map and .flatMap is always done lazily,
 * even when applied to a Now instance.
 *
 * <p>Use .map and .flatMap to chain computation, and use .value
 * to get the result when needed. It is not good style to create
 * Eval instances whose computation involves calling .value on another
 * Eval instance -- this can defeat the trampolining and lead to stack
 * overflows.
 *
 * @param <A> The type returned by this Eval
 */
public abstract class Eval<A> implements Serializable, Kind<Eval, A> {
  /**
   * Return a new Computation which calculates it's value strictly. Basically equivalent to a variable.
   *
   * @param a   The value to use as the result of this Computation
   * @param <A> The type returned by the new Eval
   * @return The new Eval
   */
  public static <A> Eval<A> now(A a) {
    return new Now<>(a);
  }

  /**
   * Return a new Computation which calculates it's value once, strictly.
   * Basically equivalent to a variable in java or a var or val in scala.
   *
   * @param a   The function to use to calculate the result of this Computation
   * @param <A> The type returned by the new Eval
   * @return The new Eval
   */
  public static <A> Eval<A> now(Supplier<A> a) {
    return now(a.get());
  }

  /**
   * Return a new Computation which calculates it's value once, lazily.
   * Basically equivalent to a lazy val in scala.
   *
   * @param a   The function to use to calculate the result of this Computation
   * @param <A> The type returned by the new Eval
   * @return The new Eval
   */
  public static <A> Eval<A> later(Supplier<A> a) {
    return new Later<>(a);
  }

  /**
   * Return a new Computation which calculates it's value on each invocation.
   * Basically equivalent to a method call.
   *
   * @param a   The function to use to calculate the result of this Computation
   * @param <A> The type returned by the new Eval
   * @return The new Eval
   */
  public static <A> Eval<A> always(Supplier<A> a) {
    return new Always<>(a);
  }

  /**
   * Evaluate the computation and return an A value.
   *
   * <p>For lazy instances (Later, Always), any necessary computation
   * will be performed at this point. For eager instances (Now), a
   * value will be immediately returned.
   *
   * @return The result of the computation
   */
  public abstract A value();

  /**
   * Transform an Eval&lt;A&gt; into an Eval&lt;B&gt; given the transformation
   * function `f`.
   *
   * <p>This call is stack-safe -- many .map calls may be chained without
   * consumed additional stack during evaluation.
   *
   * <p>Computation performed in f is always lazy, even when called on an
   * eager (Now) instance.
   *
   * @param f   the function to apply to the result of the current computation
   * @param <B> output type of the applied function
   * @return A new computation which includes the application of f
   */
  public <B> Eval<B> map(final Function<A, B> f) {
    return flatMap((A a) -> now(f.apply(a)));
  }

  /**
   * Lazily perform a computation based on an Eval&lt;A&gt;, using the
   * function `f` to produce an Eval&lt;B&gt; given an A.
   *
   * <p>This call is stack-safe -- many .flatMap calls may be chained
   * without consumed additional stack during evaluation. It is also
   * written to avoid left-association problems, so that repeated
   * calls to .flatMap will be efficiently applied.
   *
   * <p>Computation performed in f is always lazy, even when called on an
   * eager (Now) instance.
   *
   * @param f   the function to apply to the result of the current computation
   * @param <B> output type of the computation returned by the applied function
   * @return A new computation which includes the application of f
   */
  public <B> Eval<B> flatMap(final Function<A, Eval<B>> f) {
    return new Compute<>(() -> this, f);
  }

  /**
   * Ensure that the result of the computation (if any) will be
   * memoized.
   *
   * <p>Practically, this means that when called on an Always&lt;A&gt; a
   * Later&lt;A&gt; with an equivalent computation will be returned.
   *
   * @return A new, memoizing, Eval that is equivalent to the current Eval
   */
  public abstract Eval<A> memoize();


  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   * @see #hashCode()
   * @see java.util.HashMap
   */
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || !Eval.class.isAssignableFrom(o.getClass())) {
      return false;
    }

    Eval<?> other = (Eval<?>) o;

    return value().equals(other.value());
  }

  /**
   * Returns a hash code value for the object. This method is
   * supported for the benefit of hash tables such as those provided by
   * {@link java.util.HashMap}.
   *
   * @return a hash code value for this object.
   * @see java.lang.Object#equals(java.lang.Object)
   * @see java.lang.System#identityHashCode
   */
  public int hashCode() {
    return value().hashCode();
  }

  /**
   * Construct an eager Eval&lt;A&gt; instance.
   *
   * <p>In some sense it is equivalent to using a val.
   *
   * <p>This type should be used when an A value is already in hand, or
   * when the computation to produce an A value is pure and very fast.
   *
   * @param <A> The type returned by this Eval
   */
  private final static class Now<A> extends Eval<A> {
    /**
     * The value of this Eval.
     */
    private final A value;

    /**
     * Return a new Computation which calculates it's value strictly. Basically equivalent to a variable.
     *
     * @param value The value to use as the result of this Computation
     */
    Now(final A value) {
      this.value = value;
    }

    /**
     * Evaluate the computation and return an A value.
     *
     * <p>For lazy instances (Later, Always), any necessary computation
     * will be performed at this point. For eager instances (Now), a
     * value will be immediately returned.
     *
     * @return The result of the computation
     */
    @Override
    public A value() {
      return value;
    }

    /**
     * Ensure that the result of the computation (if any) will be
     * memoized.
     *
     * <p>Practically, this means that when called on an Always&lt;A&gt; a
     * Later&lt;A&gt; with an equivalent computation will be returned.
     *
     * @return A new, memoizing, Eval that is equivalent to the current Eval
     */
    @Override
    public Eval<A> memoize() {
      return this;
    }
  }

  /**
   * Construct a lazy Eval&lt;A&gt; instance.
   *
   * <p>This type should be used for most "lazy" values. In some sense it
   * is equivalent to using a lazy val.
   *
   * <p>When caching is not required or desired (e.g. if the value produced
   * may be large) prefer Always. When there is no computation
   * necessary, prefer Now.
   *
   * <p>Once Later has been evaluated, the closure (and any values captured
   * by the closure) will not be retained, and will be available for
   * garbage collection.
   *
   * @param <A> The type returned by this Eval
   */
  private final static class Later<A> extends Eval<A> {
    /**
     * The function to use in calculating the value of this Eval.
     */
    private Supplier<A> thunk;
    /**
     * The value of this Eval if it has already been computed, or None if it has not.
     */
    private Option<A> value = Option.none();

    /**
     * Return a new Computation which calculates it's value once, lazily.
     * Basically equivalent to a lazy val in scala.
     *
     * @param thunk The function to use to calculate the result of this Computation
     */
    Later(final Supplier<A> thunk) {
      this.thunk = thunk;
    }

    /**
     * Evaluate the computation and return an A value.
     *
     * <p>For lazy instances (Later, Always), any necessary computation
     * will be performed at this point. For eager instances (Now), a
     * value will be immediately returned.
     *
     * @return The result of the computation
     */
    @Override
    public A value() {
      if (!value.isDefined()) {
        value = Option.some(thunk.get());
        thunk = null;
      }
      return value.get();
    }

    /**
     * Ensure that the result of the computation (if any) will be
     * memoized.
     *
     * <p>Practically, this means that when called on an Always&lt;A&gt; a
     * Later&lt;A&gt; with an equivalent computation will be returned.
     *
     * @return A new, memoizing, Eval that is equivalent to the current Eval
     */
    @Override
    public Eval<A> memoize() {
      return this;
    }
  }

  /**
   * Construct a lazy Eval&lt;A&gt; instance.
   *
   * <p>This type can be used for "lazy" values. In some sense it is
   * equivalent to using a Supplier value.
   *
   * <p>This type will evaluate the computation every time the value is
   * required. It should be avoided except when laziness is required and
   * caching must be avoided. Generally, prefer Later.
   *
   * @param <A> The type returned by this Eval
   */
  private static final class Always<A> extends Eval<A> {
    /**
     * The function user to calculate the value of this Eval.
     */
    private final Supplier<A> f;

    /**
     * Creates an Eval that executes it's supplier every time it is called,
     * roughly equivalent to a method call.
     *
     * @param f The supplier of the value
     */
    Always(final Supplier<A> f) {
      this.f = f;
    }

    /**
     * Evaluate the computation and return an A value.
     *
     * <p>For lazy instances (Later, Always), any necessary computation
     * will be performed at this point. For eager instances (Now), a
     * value will be immediately returned.
     *
     * @return The result of the computation
     */
    @Override
    public A value() {
      return f.get();
    }

    /**
     * Ensure that the result of the computation (if any) will be
     * memoized.
     *
     * <p>Practically, this means that when called on an Always&lt;A&gt; a
     * Later&lt;A&gt; with an equivalent computation will be returned.
     *
     * @return A new, memoizing, Eval that is equivalent to the current Eval
     */
    @Override
    public Eval<A> memoize() {
      return new Later<>(f);
    }
  }

  /**
   * Compute is a type of Eval&lt;A&gt; that is used to chain computations
   * involving map() and flatMap(). Along with Eval#flatMap it
   * implements the trampoline that guarantees stack-safety.
   *
   * <p>Users should not instantiate Compute instances
   * themselves. Instead, they will be automatically created when
   * needed.
   *
   * <p>Unlike a traditional trampoline, the internal workings of the
   * trampoline are not exposed. This allows a slightly more efficient
   * implementation of the .value method.
   *
   * @param <A> The type returned by this Eval
   */
  private final static class Compute<A> extends Eval<A> {
    /**
     * The function which returns the initial Eval.
     */
    private final Supplier<Eval> start;
    /**
     * The function to apply to start's value to calculate the value of this eval.
     */
    private final Function run;

    /**
     * Creates a new Eval, based on an existing Eval and a function.
     *
     * @param start The Eval from which this Eval's computation is started
     * @param run   The function to apply to the start Eval's value to calculate this Eval
     */
    Compute(final Supplier<Eval> start, final Function run) {
      this.start = start;
      this.run = run;
    }

    /**
     * Lazily perform a computation based on an Eval&lt;A&gt;, using the
     * function `f` to produce an Eval&lt;B&gt; given an A.
     *
     * <p>This call is stack-safe -- many .flatMap calls may be chained
     * without consumed additional stack during evaluation. It is also
     * written to avoid left-association problems, so that repeated
     * calls to .flatMap will be efficiently applied.
     *
     * <p>Computation performed in f is always lazy, even when called on an
     * eager (Now) instance.
     *
     * @param f   the function to apply to the result of the current computation
     * @param <B> output type of the computation returned by the applied function
     * @return A new computation which includes the application of f
     */
    @Override
    @SuppressWarnings("unchecked")
    public <B> Eval<B> flatMap(final Function<A, Eval<B>> f) {
      return new Compute<>(start,
          s -> new Compute<>(() -> (Eval) this.run.apply(s), f));
    }

    /**
     * Ensure that the result of the computation (if any) will be
     * memoized.
     *
     * <p>Practically, this means that when called on an Always&lt;A&gt; a
     * Later&lt;A&gt; with an equivalent computation will be returned.
     *
     * @return A new, memoizing, Eval that is equivalent to the current Eval
     */
    @Override
    public Eval<A> memoize() {
      return new Later<>(this::value);
    }

    /**
     * Evaluate the computation and return an A value.
     *
     * <p>For lazy instances (Later, Always), any necessary computation
     * will be performed at this point. For eager instances (Now), a
     * value will be immediately returned.
     *
     * @return The result of the computation
     */
    @SuppressWarnings("unchecked")
    public A value() {
      final LinkedList<Function> fs = new LinkedList<>();
      Eval curr = this;
      boolean cont = true;

      while (cont) {
        if (curr instanceof Compute) {
          final Compute c = (Compute) curr;
          final Eval cstart = (Eval) c.start.get();
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
            final Function f = fs.pop();
            curr = (Eval) f.apply(curr.value());
          }
        }
      }
      return (A) curr.value();
    }
  }
}

