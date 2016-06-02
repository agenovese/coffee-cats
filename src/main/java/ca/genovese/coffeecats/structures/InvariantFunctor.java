package ca.genovese.coffeecats.structures;


import ca.genovese.coffeecats.kind.Kind;

import java.util.function.Function;

public interface InvariantFunctor<F> {
  <A, B> Kind<F, B> imap(final Kind<F, A> fa, final Function<A, B> f, final Function<B, A> g);

  /**
   * Compose 2 invariant Functors F and G to get a new Invariant Functor for F[G[_]].
   */
  default <G> InvariantFunctor<Kind<F, G>> compose(final InvariantFunctor<G> gg) {
    return new Composite<>(this, gg);
  }

  /**
   * Compose the Invariant Functor F with a normal (Covariant) Functor to get a new Invariant Functor for [F[G[_]].
   */
  @SuppressWarnings("unchecked")
  default <G> InvariantFunctor<Kind<F, G>> composeWithFunctor(final CovariantFunctor<G> G) {
    return new CovariantComposite<>(this, G);
  }

  class Composite<F, G> implements InvariantFunctor<Kind<F, G>> {
    private final InvariantFunctor<F> F;
    private final InvariantFunctor<G> G;

    Composite(final InvariantFunctor<F> f, final InvariantFunctor<G> g) {
      F = f;
      G = g;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(final Kind<Kind<F, G>, A> fga, final Function<A, B> f, final Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          (Kind<G, A> ga) -> G.imap(ga, f, g),
          (Kind<G, B> gb) -> G.imap(gb, g, f));
    }
  }

  class CovariantComposite<F, G> implements InvariantFunctor<Kind<F, G>> {
    private final InvariantFunctor<F> F;
    private final CovariantFunctor<G> G;

    CovariantComposite(final InvariantFunctor<F> f, final CovariantFunctor<G> g) {
      F = f;
      G = g;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(final Kind<Kind<F, G>, A> fga, final Function<A, B> f, final Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          (Kind<G, A> ga) -> G.map(ga, f),
          (Kind<G, B> gb) -> G.map(gb, g));
    }
  }
}
