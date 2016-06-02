package ca.genovese.coffeecats.structures;


import ca.genovese.coffeecats.data.Unit;
import ca.genovese.coffeecats.data.tuple.Tuple2;
import ca.genovese.coffeecats.kind.Kind;

import java.util.function.Function;

public interface CovariantFunctor<F> extends InvariantFunctor<F> {
  <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f);

  default <G> CovariantFunctor<Kind<F, G>> compose(CovariantFunctor<G> G) {
    return new Composite<>(this, G);
  }

  @Override
  default <G> CovariantFunctor<Kind<F, G>> composeWithFunctor(CovariantFunctor<G> G) {
    return compose(G);
  }

  default <A, B> Kind<F, B> imap(Kind<F, A> fa, Function<A, B> f, Function<B, A> g) {
    return map(fa, f);
  }

  /**
   * Lift a function f to operate on Functors
   */
  default <A, B> Function<Kind<F, A>, Kind<F, B>> lift(Function<A,B> f) {
    return (Kind<F, A> fa) -> map(fa, f);
  }

  /**
   * Empty the fa of the values, preserving the structure
   */
  default <A> Kind<F, Unit> clear(Kind<F, A> fa) {
    return map(fa, (a) -> Unit.unit);
  }

  /**
   * Tuple the values in fa with the result of applying a function
   * with the value
   */
  default <A,B> Kind<F, Tuple2<A, B>> fproduct(Kind<F, A> fa, Function<A, B> f) {
    return map(fa, a -> new Tuple2<>(a, f.apply(a)));
  }

  /**
   * Replaces the `A` value in `F[A]` with the supplied value.
   */
  default <A, B> Kind<F, B> as(Kind<F, A> fa, B b) {
    return map(fa, (a) -> b);
  }

  class Composite<F, G> implements CovariantFunctor<Kind<F, G>> {
    private final CovariantFunctor<F> F;
    private final CovariantFunctor<G> G;

    Composite(CovariantFunctor<F> f, CovariantFunctor<G> g) {
      F = f;
      G = g;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> map(Kind<Kind<F, G>, A> fa, Function<A, B> f) {
      return (Kind<Kind<F, G>, B>) F.map((Kind<F, Kind<G, A>>) fa, G.lift(f));
    }
  }
 }
