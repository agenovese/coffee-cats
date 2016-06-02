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
 * @see CovariantFunctor
 */
public interface CovariantFunctorLaws<F> extends InvariantFunctorLaws<F> {
  @Test
  default <A> void covariantIdentity(CovariantFunctor<F> F, Kind<F, A> fa) {
    assertEquals(fa, F.map(fa, Function.identity()));
  }

  @Test
  default <A, B, C> void covariantComposition(CovariantFunctor<F> F,
                                              Kind<F, A> fa,
                                              Function<A, B> f1,
                                              Function<B, C> g1) {
    assertEquals(F.map(F.map(fa, f1), g1), F.map(fa, g1.compose(f1)));
  }
}
