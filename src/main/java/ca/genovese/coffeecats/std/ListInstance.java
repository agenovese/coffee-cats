package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.CovariantFunctor;

import java.util.function.Function;

public final class ListInstance implements CovariantFunctor<List> {
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
