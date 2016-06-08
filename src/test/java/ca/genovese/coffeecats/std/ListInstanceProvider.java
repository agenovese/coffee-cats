package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.kind.Kind;


/**
 * An implementation of ParameterResolver which returns List Instances and Kinds.
 */
public final class ListInstanceProvider extends AbstractInstanceProvider<ListInstance, Kind<List, Integer>> {

  /**
   * Return an implementation of the interfaces in the structures package for List.
   *
   * @return ListInstance
   */
  @Override
  protected ListInstance instance() {
    return ListInstance.listInstance;
  }

  /**
   * Return a List.
   *
   * @return Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
   */
  @Override
  protected Kind<List, Integer> kind() {
    return List.of(1, 2, 3, 4);
  }

  /**
   * Returns a type to be used to check for implemenations
   * of interfaces in the structures package for List.
   *
   * @return ListInstance.class
   */
  @Override
  protected Class<ListInstance> type() {
    return ListInstance.class;
  }
}
