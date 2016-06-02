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
  @Test
  default <A> void invariantIdentity(InvariantFunctor<F> f, Kind<F, A> fa) {
    assertEquals(fa, f.imap(fa, Function.identity(), Function.identity()));
  }

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
