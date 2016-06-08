package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.eval.Eval;
import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.kind.Kind;

/**
 * An implementation of ParameterResolver which returns Option Instances and Kinds.
 */
public final class EvalInstanceProvider extends AbstractInstanceProvider<EvalInstance, Kind<Eval, Integer>> {

  /**
   * Return an implementation of the interfaces in the structures package for Option.
   *
   * @return OptionInstance
   */
  @Override
  protected EvalInstance instance() {
    return EvalInstance.evalInstance;
  }

  /**
   * Return an Option.
   *
   * @return Some(1)
   */
  @Override
  protected Kind<Eval, Integer> kind() {
    return Eval.now(1);
  }

  /**
   * Returns a type to be used to check for implemenations
   * of interfaces in the structures package for Option.
   *
   * @return OptionInstance.class
   */
  @Override
  protected Class<EvalInstance> type() {
    return EvalInstance.class;
  }
}
