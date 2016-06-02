package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.List;
import ca.genovese.coffeecats.kind.Kind;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * @since 5.0
 */
public class ListInstanceProvider implements ParameterResolver {

  @Override
  public boolean supports(Parameter parameter, Optional<Object> target, ExtensionContext extensionContext) {
    return isListStructure(parameter) || isListKind(parameter);
  }

  private boolean isListKind(Parameter parameter) {
    return parameter.getType().equals(Kind.class)
        && (getTypeArgName(parameter, 0).equals("F") || getTypeArgName(parameter, 0).equals("List"));
  }

  private boolean isListStructure(Parameter parameter) {
    return parameter.getType().isAssignableFrom(ListInstance.class)
        && (getTypeArgName(parameter, 0).equals("F") || getTypeArgName(parameter, 0).equals("List"));
  }

  private String getTypeArgName(Parameter parameter, int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  @Override
  public Object resolve(Parameter parameter, Optional<Object> target, ExtensionContext extensionContext) {
    if (isListStructure(parameter)) {
      return new ListInstance();
    } else if (isListKind(parameter) && getTypeArgName(parameter, 1).equals("A")) {
      return List.of(1, 2, 3, 4);
    } else return null;
  }
}
