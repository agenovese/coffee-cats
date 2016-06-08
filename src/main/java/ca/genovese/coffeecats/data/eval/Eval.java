package ca.genovese.coffeecats.data.eval;

import ca.genovese.coffeecats.kind.Kind;

import java.io.Serializable;
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
public interface Eval<A> extends Serializable, Kind<Eval, A> {
  /**
   * Return a new Computation which calculates it's value strictly. Basically equivalent to a variable.
   *
   * @param a   The value to use as the result of this Computation
   * @param <A> The type returned by the new Eval
   * @return The new Eval
   */
  static <A> Eval<A> now(A a) {
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
  static <A> Eval<A> now(Supplier<A> a) {
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
  static <A> Eval<A> later(Supplier<A> a) {
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
  static <A> Eval<A> always(Supplier<A> a) {
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
  A value();

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
  default <B> Eval<B> map(final Function<A, B> f) {
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
  default <B> Eval<B> flatMap(final Function<A, Eval<B>> f) {
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
  Eval<A> memoize();

  /**
   * Implementation function for Equals for Eval instances.
   *
   * @param o Eval to compare this to.
   * @return true if o is an Eval and returns an equal value for Eval.value
   */
  default boolean equalsCheck(Object o) {
    if (this == o) return true;
    if (o == null || !Eval.class.isAssignableFrom(o.getClass())) return false;

    Eval<?> other = (Eval<?>) o;

    return value().equals(other.value());
  }

  /**
   * Implementation function for hashCode for Eval instances.
   *
   * @return hashCode of Eval.value
   */
  default int hashCodeGen() {
    return value().hashCode();
  }
}

