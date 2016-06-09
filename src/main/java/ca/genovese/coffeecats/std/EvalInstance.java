package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.eval.Eval;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.CovariantFunctor;

import java.util.function.Function;

/**
 * An object which implements all the applicable structures for Option.
 */
public final class EvalInstance implements CovariantFunctor<Eval> {
  /**
   * A convenience instance of EvalInstance.
   */
  public static final EvalInstance evalInstance = new EvalInstance();

  /**
   * An Implementation of Map for Eval.
   *
   * @param fa  The starting Eval
   * @param f   The function to map over fa
   * @param <A> The input type
   * @param <B> The output type
   * @return An Eval with f applied
   */
  @Override
  public <A, B> Kind<Eval, B> map(final Kind<Eval, A> fa, final Function<A, B> f) {
    return ((Eval<A>) fa).map(f);
  }
}
