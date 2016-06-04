package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.data.tuple.Tuple2;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.laws.CovariantFunctorLaws;
import ca.genovese.coffeecats.structures.CovariantFunctor;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;

import static ca.genovese.coffeecats.data.option.Option.some;
import static ca.genovese.coffeecats.data.Unit.unit;
import static org.junit.gen5.api.Assertions.assertEquals;

public final class CovariantFunctorTest {
  private final CovariantFunctor<List> F = new ListInstance();

  @Test
  public void testFunctorLift() {
    assertEquals(List.of("1", "2", "3", "4"),
        F.<Integer, String>lift(Object::toString).apply(List.of(1, 2, 3, 4)));
  }

  @Test
  public void testFunctorFproduct() {
    assertEquals(List.of(new Tuple2<>(1, "1"), new Tuple2<>(2, "2"), new Tuple2<>(3, "3"), new Tuple2<>(4, "4")),
        F.fproduct(List.of(1, 2, 3, 4), Object::toString));
  }

  @Test
  public void testFunctorClear() {
    assertEquals(List.of(unit, unit, unit, unit), F.clear(List.of(1, 2, 3, 4)));
  }

  @Test
  public void testFunctorAs() {
    assertEquals(List.of("a", "a", "a", "a"), F.as(List.of(1, 2, 3, 4), "a"));
  }

  private static class ListOfOptionProvider extends AbstractInstanceProvider<CovariantFunctor, Kind<List, Option>> {
    @Override
    protected CovariantFunctor instance() {
      return new ListInstance().composeWithFunctor(new OptionInstance());
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

