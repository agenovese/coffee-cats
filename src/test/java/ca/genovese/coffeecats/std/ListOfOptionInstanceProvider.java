package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.data.Option;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.InvariantFunctor;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * @since 5.0
 */
public class ListOfOptionInstanceProvider extends AbstractInstanceProvider<InvariantFunctor, Kind<List, Option>> {
  @Override
  @SuppressWarnings("unchecked")
  protected InvariantFunctor instance() {
    InvariantFunctor<Option> optionInstance = new OptionInstance();
    return new ListInstance()
        .compose((InvariantFunctor<Kind<Option, ?>>)(Object)optionInstance);

  }

  @Override
  protected Kind<List, Option> kind() {
    return List.of(Option.of(1), Option.none(), Option.of(2), Option.of(3));
  }

  @Override
  protected Class<InvariantFunctor> type() {
    return InvariantFunctor.class;
  }
}
