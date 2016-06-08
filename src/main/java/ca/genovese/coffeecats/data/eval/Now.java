package ca.genovese.coffeecats.data.eval;

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
final class Now<A> implements Eval<A> {
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

  @Override
  public boolean equals(Object o) {
    return equalsCheck(o);
  }

  @Override
  public int hashCode() {
    return hashCodeGen();
  }
}
