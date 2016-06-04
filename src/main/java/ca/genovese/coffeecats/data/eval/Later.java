package ca.genovese.coffeecats.data.eval;

import ca.genovese.coffeecats.data.option.Option;

import java.util.function.Supplier;

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

  Later(final Supplier<A> thunk) {
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
