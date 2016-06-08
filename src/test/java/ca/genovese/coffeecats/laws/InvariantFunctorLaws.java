package ca.genovese.coffeecats.laws;

import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.InvariantFunctor;
import org.junit.gen5.api.Test;

import java.util.function.Function;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * Laws that must be obeyed by any
 * `ca.genovese.coffeecats.structures.InvariantFunctor`.
 *
 * @see ca.genovese.coffeecats.structures.InvariantFunctor
 */
public interface InvariantFunctorLaws<F> {

  /**
   * The Identity law states that imapping over the identity
   * function should return the original value.
   *
   * @param f instance of {@code CovariantFunctor<F>}
   * @param fa instance of {@code F<A>}
   * @param <A> type of the values in fa
   */
  @Test
  default <A> void invariantIdentity(InvariantFunctor<F> f, Kind<F, A> fa) {
    assertEquals(fa, f.imap(fa, Function.identity(), Function.identity()));
  }

  /**
   * The composition law stats that imapping one function pair
   * over the result of imapping another function pair should
   * produce the same result as mapping over the composition of
   * those function pairs.
   *
   * @param f a {@code CovariantFunctor<F>}
   * @param fa the input {@code F<A>}
   * @param f1 the first covariant function
   * @param f2 the first contravariant function
   * @param g1 the second covariant function
   * @param g2 the second contravariant function
   * @param <A> the input type
   * @param <B> the intermediate type
   * @param <C> the output type
   *
   */
  @Test
  default <A, B, C> void invariantComposition(InvariantFunctor<F> f,
                                              Kind<F, A> fa,
                                              Function<A, B> f1,
                                              Function<B, A> f2,
                                              Function<B, C> g1,
                                              Function<C, B> g2) {
    assertEquals(f.imap(f.imap(fa, f1, f2), g1, g2), f.imap(fa, g1.compose(f1), f2.compose(g2)));
  }
}
