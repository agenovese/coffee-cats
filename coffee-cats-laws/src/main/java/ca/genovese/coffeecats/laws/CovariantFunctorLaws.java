package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.CovariantFunctor;
import org.junit.gen5.api.Test;

import java.util.function.Function;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * Laws that must be obeyed by any
 * `ca.genovese.coffeecats.structures.CovariantFunctor`.
 *
 * @param <F> Type for which the CovariantFunctor is being tested
 * @see CovariantFunctor
 */
public interface CovariantFunctorLaws<F> extends InvariantFunctorLaws<F> {

  /**
   * The Identity law states that mapping over the identity function should return the original value.
   *
   * @param f instance of {@code CovariantFunctor<F>}
   * @param fa instance of {@code F<A>}
   * @param <A> type of the values in fa
   */
  @Test
  default <A> void covariantIdentity(CovariantFunctor<F> f, Kind<F, A> fa) {
    assertEquals(fa, f.map(fa, Function.identity()));
  }

  /**
   * The composition law stats that mapping one function
   * over the result of mapping another function should
   * produce the same result as mapping over the composition of
   * those functions.
   *
   * @param f a {@code CovariantFunctor<F>}
   * @param fa the input {@code F<A>}
   * @param f1 the first function
   * @param g1 the second function
   * @param <A> the input type
   * @param <B> the intermediate type
   * @param <C> the output type
   */
  @Test
  default <A, B, C> void covariantComposition(CovariantFunctor<F> f,
                                              Kind<F, A> fa,
                                              Function<A, B> f1,
                                              Function<B, C> g1) {
    assertEquals(f.map(f.map(fa, f1), g1), f.map(fa, g1.compose(f1)));
  }
}
