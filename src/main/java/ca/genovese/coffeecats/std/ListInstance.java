package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.CovariantFunctor;
import ca.genovese.coffeecats.structures.InvariantFunctor;

import java.util.function.Function;

public class ListInstance implements CovariantFunctor<List> {
  @Override
  public <A, B> Kind<List, B> map(Kind<List, A> fa, Function<A, B> f) {
    List<B> result = List.of();

    @SuppressWarnings("unchecked")
    List<A> realType = fa.getRealType().reverse();

    for (A a : realType) {
      result = List.cons(f.apply(a), result);
    }

    return result;
  }
}
