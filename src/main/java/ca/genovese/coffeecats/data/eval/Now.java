package ca.genovese.coffeecats.data.eval;

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

  Now(final A value) {
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
