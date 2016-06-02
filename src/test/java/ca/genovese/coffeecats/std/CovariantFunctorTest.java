package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.data.Option;
import ca.genovese.coffeecats.data.tuple.Tuple2;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.laws.CovariantFunctorLaws;
import ca.genovese.coffeecats.structures.CovariantFunctor;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import java.util.function.Function;

import static ca.genovese.coffeecats.data.Option.some;
import static ca.genovese.coffeecats.data.Unit.unit;
import static org.junit.gen5.api.Assertions.assertEquals;

public class CovariantFunctorTest {

  @Test
  public void testFunctorLift() {
    List<Integer> fa = List.of(1, 2, 3, 4);
    CovariantFunctor<List> F = new ListInstance();
    Function<Kind<List, Integer>, Kind<List, String>> f = F.lift(Object::toString);
    assertEquals(List.of("1", "2", "3", "4"), f.apply(fa));
  }

  @Test
  public void testFunctorFproduct() {
    List<Integer> fa = List.of(1, 2, 3, 4);
    CovariantFunctor<List> F = new ListInstance();

    assertEquals(List.of(new Tuple2<>(1, "1"), new Tuple2<>(2, "2"), new Tuple2<>(3, "3"), new Tuple2<>(4, "4")),
        F.fproduct(fa, Object::toString));
  }

  @Test
  public void testFunctorClear() {
    List<Integer> fa = List.of(1, 2, 3, 4);
    CovariantFunctor<List> F = new ListInstance();
    assertEquals(List.of(unit, unit, unit, unit), F.clear(fa));
  }

  @Test
  public void testFunctorAs() {
    List<Integer> fa = List.of(1, 2, 3, 4);
    CovariantFunctor<List> F = new ListInstance();
    assertEquals(List.of("a", "a", "a", "a"), F.as(fa, "a"));
  }

  private static class ListOfOptionProvider extends AbstractInstanceProvider<CovariantFunctor, Kind<List, Option>> {
    @Override
    protected CovariantFunctor instance() {
      CovariantFunctor<Option> optionInstance = new OptionInstance();
      CovariantFunctor<List> listInstance = new ListInstance();
      return listInstance.composeWithFunctor(optionInstance);
    }

    @Override
    protected Kind<List, Option> kind() {
      return List.of(some(1), some(2), some(3), some(4));
    }

    @Override
    protected Class<CovariantFunctor> type() {
      return CovariantFunctor.class;
    }
  }

  @Nested
  @ExtendWith(ListOfOptionProvider.class)
  @ExtendWith(FunctionProvider.class)
  public class CompositeTest implements CovariantFunctorLaws<Kind<List, Option>> {

  }
}
