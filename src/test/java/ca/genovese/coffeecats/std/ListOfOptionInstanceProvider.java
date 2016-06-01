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
public class ListOfOptionInstanceProvider implements ParameterResolver {

  @Override
  public boolean supports(Parameter parameter, Optional<Object> target, ExtensionContext extensionContext) {
    return isListOfOptionStructure(parameter) || isListOfOptionKind(parameter);
  }

  private boolean isListOfOptionKind(Parameter parameter) {
    return parameter.getType().equals(Kind.class)
        && getTypeArgName(parameter, 0).equals("F");
  }

  private boolean isListOfOptionStructure(Parameter parameter) {
    return parameter.getType().isAssignableFrom(InvariantFunctor.class)
        && getTypeArgName(parameter, 0).equals("F");
  }

  private String getTypeArgName(Parameter parameter, int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object resolve(Parameter parameter, Optional<Object> target, ExtensionContext extensionContext) {
    if (isListOfOptionStructure(parameter)) {
      InvariantFunctor<Option> optionInstance = new OptionInstance();
      return new ListInstance()
          .compose((InvariantFunctor<Kind<Option, ?>>)(Object)optionInstance);
    } else if (isListOfOptionKind(parameter) && getTypeArgName(parameter, 1).equals("A")) {
      return List.of(Option.of(1), Option.none(), Option.of(2), Option.of(3));
    } else return null;
  }
}
