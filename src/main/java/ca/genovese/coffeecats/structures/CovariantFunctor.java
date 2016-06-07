package ca.genovese.coffeecats.structures;


import ca.genovese.coffeecats.data.Unit;
import ca.genovese.coffeecats.data.tuple.Tuple2;
import ca.genovese.coffeecats.kind.Kind;

import java.util.function.Function;

/**
 * A CovariantFunctor.
 *
 * <p>The name is often shortened to "functor".
 *
 * <p>Must obey the laws defined in CovariantFunctorLaws.
 *
 * @param <F> the datatype for this Functor
 */
public interface CovariantFunctor<F> extends InvariantFunctor<F> {
  /**
   * Apply the given function in the context of this Functor.
   *
   * @param fa  an {@code F<A>}
   * @param f   the function to apply
   * @param <A> the input type
   * @param <B> the output type
   * @return an {@code F<B>}
   */
  <A, B> Kind<F, B> map(final Kind<F, A> fa, final Function<A, B> f);


  /**
   * A function that creates a composed Functor from this and another CovariantFunctor.
   *
   * @param g   the CovariantFunctor Implementation to compose with this
   * @param <G> the type for which you have a CovariantFunctor to compose with this
   * @return a {@code CovariantFunctor<F<G<?>>}
   */
  default <G> CovariantFunctor<Kind<F, G>> compose(final CovariantFunctor<G> g) {
    return new Composite<>(this, g);
  }

  /**
   * An overrided implementation of InvariantFunctor.composeWithFunctor that
   * takes advantage of the simpler composition of 2 CovariantFunctors.
   *
   * @param g   the CovariantFunctor Implementation to compose with this
   * @param <G> the type for which you have a CovariantFunctor to compose with this
   * @return a {@code CovariantFunctor<F<G<?>>}
   */
  @Override
  default <G> CovariantFunctor<Kind<F, G>> composeWithFunctor(final CovariantFunctor<G> g) {
    return compose(g);
  }

  /**
   * Implementation of InvariantFunctor.imap derived from CovariantFunctor.map.
   *
   * @param fa  an {@code F<A>} as input
   * @param f   A function from A to B
   * @param g   A function from B to A
   * @param <A> the input type
   * @param <B> the output type
   * @return an {@code F<B>}
   */
  default <A, B> Kind<F, B> imap(final Kind<F, A> fa, final Function<A, B> f, final Function<B, A> g) {
    return map(fa, f);
  }

  /**
   * Lift a function f to operate on Functors.
   *
   * @param f   The function to be lifted
   * @param <A> The input type
   * @param <B> The output type
   * @return The lifted function
   */
  default <A, B> Function<Kind<F, A>, Kind<F, B>> lift(final Function<A, B> f) {
    return (Kind<F, A> fa) -> map(fa, f);
  }

  /**
   * Empty the fa of the values, preserving the structure.
   *
   * @param fa  the structure to empty
   * @param <A> the type of the values in fa
   * @return an {@code f<Unit>} that matches the structure of fa
   */
  default <A> Kind<F, Unit> clear(final Kind<F, A> fa) {
    return as(fa, Unit.unit);
  }

  /**
   * Tuple the values in fa with the result of applying a function with the value.
   *
   * @param fa  the input {@code F<A>}
   * @param f   the function to apply
   * @param <A> the type of the values in fa
   * @param <B> the output type of f
   * @return an {@code F<Tuple<A, B>>} containing the values from fa as the first
   *     element in the tuple and the output of f.apply(a) as the second argument.
   */
  default <A, B> Kind<F, Tuple2<A, B>> fproduct(final Kind<F, A> fa, final Function<A, B> f) {
    return map(fa, a -> new Tuple2<>(a, f.apply(a)));
  }

  /**
   * Replaces the `A` value in `F[A]` with the supplied value.
   *
   * @param fa  the input {@code F<A>}
   * @param b   the value to insert into a new F with the same structure as the input fa
   * @param <A> the type of the values in fa
   * @param <B> the type of the value to insert
   * @return an {@code F<B>} with the same structure as fa.
   */
  default <A, B> Kind<F, B> as(final Kind<F, A> fa, B b) {
    return map(fa, (a) -> b);
  }

  /**
   * An implementation of CovariantFunctor that combines 2 existing
   * Covariant Functors and applies to the data types in a nested way.
   *
   * @param <F> the outer data type
   * @param <G> the inner data type
   */
  class Composite<F, G> implements CovariantFunctor<Kind<F, G>> {
    /**
     * the outer data type's CovariantFunctor implementation.
     */
    private final CovariantFunctor<F> F;
    /**
     * the inner data type's CovariantFunctor implementation.
     */
    private final CovariantFunctor<G> G;

    /**
     * Constructor.
     *
     * @param f the outer data type's CovariantFunctor implementation
     * @param g the inner data type's CovariantFunctor implementation
     */
    Composite(final CovariantFunctor<F> f, final CovariantFunctor<G> g) {
      F = f;
      G = g;
    }

    /**
     * A map implementation for {@code F<G<?>>}.
     *
     * @param fa  an {@code F<G<A>>}
     * @param f   the function to apply
     * @param <A> the input type
     * @param <B> the output type
     * @return an {@code F<G<B>>}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <A, B> Kind<Kind<F, G>, B> map(final Kind<Kind<F, G>, A> fa, final Function<A, B> f) {
      return (Kind<Kind<F, G>, B>) F.map((Kind<F, Kind<G, A>>) fa, G.lift(f));
    }
  }
}
