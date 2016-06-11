package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.kind.Kind;

/**
 * An implementation of ParameterResolver which returns Option Instances and Kinds.
 */
public final class OptionInstanceProvider extends AbstractInstanceProvider<OptionInstance, Kind<Option, Integer>> {

  /**
   * Return an implementation of the interfaces in the structures package for Option.
   *
   * @return OptionInstance
   */
  @Override
  protected OptionInstance instance() {
    return OptionInstance.optionInstance;
  }

  /**
   * Return an Option.
   *
   * @return Some(1)
   */
  @Override
  protected Kind<Option, Integer> kind() {
    return Option.of(1);
  }

  /**
   * Returns a type to be used to check for implemenations
   * of interfaces in the structures package for Option.
   *
   * @return OptionInstance.class
   */
  @Override
  protected Class<OptionInstance> type() {
    return OptionInstance.class;
  }
}
