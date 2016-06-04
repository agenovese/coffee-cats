package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.data.Option;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.laws.InvariantFunctorLaws;
import ca.genovese.coffeecats.structures.CovariantFunctor;
import ca.genovese.coffeecats.structures.InvariantFunctor;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.extension.ExtendWith;

import java.util.function.Function;

import static ca.genovese.coffeecats.data.Option.some;

public class InvariantFunctorTest {

  private static class InvariantCompositeProvider
      extends AbstractInstanceProvider<InvariantFunctor, Kind<List, Option>> {
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

    @Override
    protected Kind<List, Option> kind() {
      return List.of(some(1), some(2), some(3), some(4));
    }

    @Override
    protected Class<InvariantFunctor> type() {
      return InvariantFunctor.class;
    }
  }


  @Nested
  @ExtendWith(InvariantCompositeProvider.class)
  @ExtendWith(FunctionProvider.class)
  public class CompositeTest implements InvariantFunctorLaws<Kind<List, Option>> {

  }

  private static class CovariantCompositeProvider
      extends AbstractInstanceProvider<InvariantFunctor, Kind<List, Option>> {
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

    @Override
    protected Kind<List, Option> kind() {
      return List.of(some(1), some(2), some(3), some(4));
    }

    @Override
    protected Class<InvariantFunctor> type() {
      return InvariantFunctor.class;
    }
  }

  @Nested
  @ExtendWith(CovariantCompositeProvider.class)
  @ExtendWith(FunctionProvider.class)
  public class CovariantCompositeTest implements InvariantFunctorLaws<Kind<List, Option>> {

  }
}