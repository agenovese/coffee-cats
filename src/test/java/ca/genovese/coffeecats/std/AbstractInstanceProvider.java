package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.kind.Kind;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * @since 5.0
 */
public abstract class AbstractInstanceProvider<S, F> implements ParameterResolver {
  protected abstract S instance();

  protected abstract F kind();

  protected abstract Class<S> type();

  @Override
  public boolean supports(final Parameter parameter,
                          final Optional<Object> target,
                          final ExtensionContext extensionContext) {
    return isInstance(parameter) || isKind(parameter);
  }

  private boolean isKind(final Parameter parameter) {
    return parameter.getType().equals(Kind.class)
        && getTypeArgName(parameter, 0).equals("F");
  }

  private boolean isInstance(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(type())
        && getTypeArgName(parameter, 0).equals("F");
  }

  private String getTypeArgName(final Parameter parameter, final int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object resolve(final Parameter parameter,
                        final Optional<Object> target,
                        final ExtensionContext extensionContext) {
    if (isInstance(parameter)) {
      return instance();
    } else if (isKind(parameter) && getTypeArgName(parameter, 1).equals("A")) {
      return kind();
    } else {
      return null;
    }
  }
}
