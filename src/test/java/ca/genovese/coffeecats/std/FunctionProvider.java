package ca.genovese.coffeecats.std;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.function.Function;

/**
 * @since 5.0
 */
public class FunctionProvider implements ParameterResolver {

  @Override
  public boolean supports(final Parameter parameter,
                          final Optional<Object> target,
                          final ExtensionContext extensionContext) {
    return isFunctionAB(parameter)
        || isFunctionBA(parameter)
        || isFunctionBC(parameter)
        || isFunctionCB(parameter);
  }

  private boolean isFunctionAB(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("A")
        && getTypeArgName(parameter, 1).equals("B");
  }

  private boolean isFunctionBA(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("B")
        && getTypeArgName(parameter, 1).equals("A");
  }

  private boolean isFunctionBC(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("B")
        && getTypeArgName(parameter, 1).equals("C");
  }

  private boolean isFunctionCB(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("C")
        && getTypeArgName(parameter, 1).equals("B");
  }

  private String getTypeArgName(final Parameter parameter, final int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  @Override
  public Object resolve(final Parameter parameter,
                        final Optional<Object> target,
                        final ExtensionContext extensionContext) {
    if (isFunctionAB(parameter)) {
      return (Function<Integer, String>) Object::toString;
    } else if (isFunctionBA(parameter)) {
      return (Function<String, Integer>) String::length;
    } else if (isFunctionBC(parameter)) {
      return (Function<String, Long>) (s) -> (long) s.length();
    } else if (isFunctionCB(parameter)) {
      return (Function<Long, String>) Object::toString;
    } else return null;
  }
}
