package ca.genovese.coffeecats.data.eval;

import ca.genovese.coffeecats.data.option.Option;

import java.util.function.Supplier;

/**
 * Construct a lazy Eval&lt;A&gt; instance.
 * <p>This type should be used for most "lazy" values. In some sense it
 * is equivalent to using a lazy val.
 * <p>When caching is not required or desired (e.g. if the value produced
 * may be large) prefer Always. When there is no computation
 * necessary, prefer Now.
 * <p>Once Later has been evaluated, the closure (and any values captured
 * by the closure) will not be retained, and will be available for
 * garbage collection.
 *
 * @param <A> The type returned by this Eval
 */
final class Later<A> implements Eval<A> {
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
