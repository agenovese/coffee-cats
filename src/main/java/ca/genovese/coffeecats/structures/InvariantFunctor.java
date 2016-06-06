package ca.genovese.coffeecats.structures;


import ca.genovese.coffeecats.kind.Kind;

import java.util.function.Function;

/**
 * InvariantFunctor.
 * <p>
 * Must obey the laws defined in InvariantLaws
 *
 * @param <F> the type for which this is an InvariantFunctor.
 */
public interface InvariantFunctor<F> {
  <A, B> Kind<F, B> imap(final Kind<F, A> fa, final Function<A, B> f, final Function<B, A> g);

  /**
   * Compose 2 invariant Functors F and G to get a new Invariant Functor for F[G[_]].
   *
   * @param g   the InvariantFunctor Implementation to compose with this
   * @param <G> the type for the InvariantFunctor Implementation to compose with this
   * @return an {@code InvariantFunctor<F<G<?>>>}
   */
  default <G> InvariantFunctor<Kind<F, G>> compose(final InvariantFunctor<G> g) {
    return new Composite<>(this, g);
  }

  /**
   * Compose the Invariant Functor F with a normal (Covariant) Functor to get a new Invariant Functor for [F[G[_]].
   *
   * @param g   the CovariantFunctor Implementation to compose with this
   * @param <G> the type for the CovariantFunctor Implementation to compose with this
   * @return an {@code InvariantFunctor<F<G<?>>>}
   */
  @SuppressWarnings("unchecked")
  default <G> InvariantFunctor<Kind<F, G>> composeWithFunctor(final CovariantFunctor<G> g) {
    return new CovariantComposite<>(this, g);
  }

  /**
   * An implementation of InvariantFunctor that combines 2 existing
   * InvariantFunctors and applies to the data types in a nested way.
   *
   * @param <F> the outer data type
   * @param <G> the inner data type
   */
  class Composite<F, G> implements InvariantFunctor<Kind<F, G>> {
    /**
     * the outer data type's InvariantFunctor implementation
     */
    private final InvariantFunctor<F> F;
    /**
     * the inner data type's InvariantFunctor implementation
     */
    private final InvariantFunctor<G> G;

    /**
     * Constructor
     *
     * @param f the outer data type's InvariantFunctor implementation
     * @param g the inner data type's InvariantFunctor implementation
     */
    Composite(final InvariantFunctor<F> f, final InvariantFunctor<G> g) {
      F = f;
      G = g;
    }

    /**
     * An imap implementation for {@code F<G<?>>}
     *
     * @param fga an {@code F<G<A>>}
     * @param f   the function to apply Covariantly
     * @param g   the function to apply Contravariantly
     * @param <A> the input type
     * @param <B> the output type
     * @return an {@code F<G<B>>}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(final Kind<Kind<F, G>, A> fga, final Function<A, B> f, final Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          (Kind<G, A> ga) -> G.imap(ga, f, g),
          (Kind<G, B> gb) -> G.imap(gb, g, f));
    }
  }

  /**
   * An implementation of InvariantFunctor that combines an existing
   * InvariantFunctor with an existing CovariantFunctor and applies to the data types in a nested way.
   *
   * @param <F> the outer data type
   * @param <G> the inner data type
   */
  class CovariantComposite<F, G> implements InvariantFunctor<Kind<F, G>> {
    /**
     * the outer data type's InvariantFunctor implementation
     */
    private final InvariantFunctor<F> F;
    /**
     * the inner data type's CovariantFunctor implementation
     */
    private final CovariantFunctor<G> G;

    /**
     * Constructor
     *
     * @param f the outer data type's InvariantFunctor implementation
     * @param g the inner data type's CovariantFunctor implementation
     */
    CovariantComposite(final InvariantFunctor<F> f, final CovariantFunctor<G> g) {
      F = f;
      G = g;
    }

    /**
     * An imap implementation for {@code F<G<?>>}
     *
     * @param fga an {@code F<G<A>>}
     * @param f   the function to apply Covariantly
     * @param g   the function to apply Contravariantly
     * @param <A> the input type
     * @param <B> the output type
     * @return an {@code F<G<B>>}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> imap(final Kind<Kind<F, G>, A> fga, final Function<A, B> f, final Function<B, A> g) {
      return (Kind<Kind<F, G>, B>) F.imap((Kind<F, Kind<G, A>>) fga,
          (Kind<G, A> ga) -> G.map(ga, f),
          (Kind<G, B> gb) -> G.map(gb, g));
    }
  }
}
