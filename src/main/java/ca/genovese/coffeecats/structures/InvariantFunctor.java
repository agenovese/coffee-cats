package ca.genovese.coffeecats.structures;


import ca.genovese.coffeecats.kind.Kind;

import java.util.function.Function;

public interface InvariantFunctor<F extends Kind> {
  <A, B> Kind<F, B> imap(Kind<F, A> fa, Function<A, B> f, Function<B, A> g);

  /**
   * Compose 2 invariant Functors F and G to get a new Invariant Functor for F[G[_]].
   */
  default <G> InvariantFunctor<Kind<F, Kind<G, ?>>> compose(InvariantFunctor<Kind<G, ?>> gg) {
    return new Composite<>(this, gg);
  }

  class Composite<F extends Kind, G extends Kind> implements InvariantFunctor<Kind<F, G>> {
    private final InvariantFunctor<F> F;
    private final InvariantFunctor<G> G;

    public Composite(InvariantFunctor<F> f, InvariantFunctor<G> g) {
      F = f;
      G = g;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(Kind<Kind<F, G>, A> fga, Function<A, B> f, Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          ga -> G.imap((Kind<G, A>) ga, f, g),
          gb -> G.imap((Kind<G, B>) gb, g, f));
    }
  }
}
