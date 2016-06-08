package ca.genovese.coffeecats.data.eval;

import java.util.function.Supplier;

/**
 * /**
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
final class Always<A> implements Eval<A> {
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

  @Override
  public boolean equals(Object o) {
    return equalsCheck(o);
  }

  @Override
  public int hashCode() {
    return hashCodeGen();
  }
}
