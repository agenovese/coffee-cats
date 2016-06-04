package ca.genovese.coffeecats.data.eval;

import java.util.function.Supplier;

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

  Always(final Supplier<A> f) {
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
