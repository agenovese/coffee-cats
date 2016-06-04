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
  public boolean supports(final Parameter parameter,
                          final Optional<Object> target,
                          final ExtensionContext extensionContext) {
    return isListStructure(parameter) || isListKind(parameter);
  }

  private boolean isListKind(final Parameter parameter) {
    return parameter.getType().equals(Kind.class)
        && (getTypeArgName(parameter, 0).equals("F") || getTypeArgName(parameter, 0).equals("List"));
  }

  private boolean isListStructure(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(ListInstance.class)
        && (getTypeArgName(parameter, 0).equals("F") || getTypeArgName(parameter, 0).equals("List"));
  }

  private String getTypeArgName(final Parameter parameter, final int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  @Override
  public Object resolve(final Parameter parameter,
                        final Optional<Object> target,
                        final ExtensionContext extensionContext) {
    if (isListStructure(parameter)) {
      return new ListInstance();
    } else if (isListKind(parameter) && getTypeArgName(parameter, 1).equals("A")) {
      return List.of(1, 2, 3, 4);
    } else return null;
  }
}
