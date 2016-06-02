package ca.genovese.coffeecats.structures;


import ca.genovese.coffeecats.kind.Kind;

import java.util.function.Function;

public interface InvariantFunctor<F> {
  <A, B> Kind<F, B> imap(Kind<F, A> fa, Function<A, B> f, Function<B, A> g);

  /**
   * Compose 2 invariant Functors F and G to get a new Invariant Functor for F[G[_]].
   */
  default <G> InvariantFunctor<Kind<F, Kind<G, ?>>> compose(InvariantFunctor<Kind<G, ?>> gg) {
    return new Composite<>(this, gg);
  }

  /**
   * Compose the Invariant Functor F with a normal (Covariant) Functor to get a new Invariant Functor for [F[G[_]].
   */
  @SuppressWarnings("unchecked")
  default <G> InvariantFunctor<Kind<F, G>> composeWithFunctor(CovariantFunctor<G> G) {
    return new CovariantComposite<>(this, G);
  }

  class Composite<F, G> implements InvariantFunctor<Kind<F, G>> {
    private final InvariantFunctor<F> F;
    private final InvariantFunctor<G> G;

    Composite(InvariantFunctor<F> f, InvariantFunctor<G> g) {
      F = f;
      G = g;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(Kind<Kind<F, G>, A> fga, Function<A, B> f, Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          (Kind<G,A> ga) -> G.imap(ga, f, g),
          (Kind<G,B> gb) -> G.imap(gb, g, f));
    }
  }

  class CovariantComposite<F, G> implements InvariantFunctor<Kind<F, G>> {
    private final InvariantFunctor<F> F;
    private final CovariantFunctor<G> G;

    CovariantComposite(InvariantFunctor<F> f, CovariantFunctor<G> g) {
      F = f;
      G = g;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(Kind<Kind<F, G>, A> fga, Function<A, B> f, Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          (Kind<G,A> ga) -> G.map(ga, f),
          (Kind<G,B> gb) -> G.map(gb, g));
    }
  }
}
