package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.kind.Kind;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * @since 5.0
 */
public final class OptionInstanceProvider implements ParameterResolver {

  @Override
  public boolean supports(final Parameter parameter,
                          final Optional<Object> target,
                          final ExtensionContext extensionContext) {
    return isOptionStructure(parameter) || isOptionKind(parameter);
  }

  private boolean isOptionKind(final Parameter parameter) {
    return parameter.getType().equals(Kind.class)
        && (getTypeArgName(parameter, 0).equals("F") || getTypeArgName(parameter, 0).equals("Option"));
  }

  private boolean isOptionStructure(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(OptionInstance.class)
        && (getTypeArgName(parameter, 0).equals("F") || getTypeArgName(parameter, 0).equals("Option"));
  }

  private String getTypeArgName(final Parameter parameter, final int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  @Override
  public Object resolve(final Parameter parameter,
                        final Optional<Object> target,
                        final ExtensionContext extensionContext) {
    if (isOptionStructure(parameter)) {
      return new OptionInstance();
    } else if (isOptionKind(parameter) && getTypeArgName(parameter, 1).equals("A")) {
      return Option.of(1);
    } else {
      return null;
    }
  }
}
