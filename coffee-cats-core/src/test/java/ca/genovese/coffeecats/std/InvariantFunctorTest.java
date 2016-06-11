package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.laws.InvariantFunctorLaws;
import ca.genovese.coffeecats.structures.CovariantFunctor;
import ca.genovese.coffeecats.structures.InvariantFunctor;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.extension.ExtendWith;

import java.util.function.Function;

import static ca.genovese.coffeecats.data.option.Option.some;

/**
 * Test the derived methods in InvariantFunctor.
 */
public class InvariantFunctorTest {

  /**
   * A ParameterResolver providing a composite of 2 InvariantFunctors.
   */
  private static class InvariantCompositeProvider
      extends AbstractInstanceProvider<InvariantFunctor, Kind<List, Option>> {
    /**
     * Create an instance of a composite of 2 InvariantFunctors.
     *
     * @return a composite of 2 InvariantFunctors
     */
    @Override
    protected InvariantFunctor instance() {
      final InvariantFunctor<Option> optionInstance = new InvariantFunctor<Option>() {
        @Override
        public <A, B> Kind<Option, B> imap(final Kind<Option, A> fa, final Function<A, B> f, final Function<B, A> g) {
          return new OptionInstance().imap(fa, f, g);
        }
      };
      final InvariantFunctor<List> listInstance = new InvariantFunctor<List>() {
        @Override
        public <A, B> Kind<List, B> imap(final Kind<List, A> fa, final Function<A, B> f, final Function<B, A> g) {
          return new ListInstance().imap(fa, f, g);
        }
      };
      return listInstance.compose(optionInstance);
    }

    /**
     * A Nested data structure for the composite InvariantFunctor.
     *
     * @return A {@code List<Option<Integer>>}
     */
    @Override
    protected Kind<List, Option> kind() {
      return List.of(some(1), some(2), some(3), some(4));
    }

    /**
     * InvariantFunctor.class.
     *
     * @return InvariantFunctor.class
     */
    @Override
    protected Class<InvariantFunctor> type() {
      return InvariantFunctor.class;
    }
  }

  /**
   * Test that the Invariant Composite follows the InvariantFunctorLaws.
   */
  @Nested
  @ExtendWith(InvariantCompositeProvider.class)
  @ExtendWith(FunctionProvider.class)
  public class CompositeTest implements InvariantFunctorLaws<Kind<List, Option>> {

  }

  /**
   * A ParameterResolver providing a composite of an InvariantFunctor with a CovariantFunctor.
   */
  private static class CovariantCompositeProvider
      extends AbstractInstanceProvider<InvariantFunctor, Kind<List, Option>> {

    /**
     * Create an instance of a composite of an InvariantFunctor with a CovariantFunctor.
     *
     * @return a composite of an InvariantFunctor with a CovariantFunctor
     */
    @Override
    protected InvariantFunctor instance() {
      final CovariantFunctor<Option> optionInstance = new OptionInstance();
      final InvariantFunctor<List> listInstance = new InvariantFunctor<List>() {
        @Override
        public <A, B> Kind<List, B> imap(final Kind<List, A> fa, final Function<A, B> f, final Function<B, A> g) {
          return new ListInstance().imap(fa, f, g);
        }
      };
      return listInstance.composeWithFunctor(optionInstance);
    }

    /**
     * A Nested data structure for the composite InvariantFunctor.
     *
     * @return A {@code List<Option<Integer>>}
     */
    @Override
    protected Kind<List, Option> kind() {
      return List.of(some(1), some(2), some(3), some(4));
    }

    /**
     * InvariantFunctor.class.
     *
     * @return InvariantFunctor.class
     */
    @Override
    protected Class<InvariantFunctor> type() {
      return InvariantFunctor.class;
    }
  }

  /**
   * Test that the Invariant/Covariant Composite follows the InvariantFunctorLaws.
   */
  @Nested
  @ExtendWith(CovariantCompositeProvider.class)
  @ExtendWith(FunctionProvider.class)
  public class CovariantCompositeTest implements InvariantFunctorLaws<Kind<List, Option>> {

  }
}
