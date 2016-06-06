package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.CovariantFunctor;

import java.util.function.Function;

/**
 * An object which implements all the applicable structures for List.
 */
public final class ListInstance implements CovariantFunctor<List> {
  /**
   * A convenience instance of ListInstance.
   */
  public static final ListInstance listInstance = new ListInstance();

  /**
   * An Implementation of Map for List.
   *
   * @param fa The starting List
   * @param f The function to map over fa
   * @param <A> The input type
   * @param <B> The output type
   * @return A List of the values from fa after having had f applied to them
   */
  @Override
  public <A, B> Kind<List, B> map(final Kind<List, A> fa, final Function<A, B> f) {
    List<B> result = List.of();

    @SuppressWarnings("unchecked")
    final List<A> realType = fa.getRealType().reverse();

    for (final A a : realType) {
      result = List.cons(f.apply(a), result);
    }

    return result;
  }
}
