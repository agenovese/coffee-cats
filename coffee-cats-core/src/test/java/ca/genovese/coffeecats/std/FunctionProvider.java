package ca.genovese.coffeecats.std;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.function.Function;

/**
 * An implementation of ParameterResolver for Functions.
 */
public final class FunctionProvider implements ParameterResolver {

  /**
   * A method which indicates whether this ParameterResolver can resolve the given parameter.
   *
   * @param parameter the parameter to resolve
   * @param target the object to resolve it to
   * @param extensionContext the extensionContext
   * @return A boolean indicating whether this ParameterResolver can resolve the given parameter
   */
  @Override
  public boolean supports(final Parameter parameter,
                          final Optional<Object> target,
                          final ExtensionContext extensionContext) {
    return isFunctionAB(parameter)
        || isFunctionBA(parameter)
        || isFunctionBC(parameter)
        || isFunctionCB(parameter);
  }

  /**
   * Is the paramater a {@code Function<A, B>}.
   *
   * @param parameter The parameter to test
   * @return true if the paramater a {@code Function<A, B>}
   */
  private boolean isFunctionAB(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("A")
        && getTypeArgName(parameter, 1).equals("B");
  }

  /**
   * Is the paramater a {@code Function<B, A>}.
   *
   * @param parameter The parameter to test
   * @return true if the paramater a {@code Function<B, A>}
   */
  private boolean isFunctionBA(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("B")
        && getTypeArgName(parameter, 1).equals("A");
  }

  /**
   * Is the paramater a {@code Function<B, C>}.
   *
   * @param parameter The parameter to test
   * @return true if the paramater a {@code Function<B, C>}
   */
  private boolean isFunctionBC(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("B")
        && getTypeArgName(parameter, 1).equals("C");
  }

  /**
   * Is the paramater a {@code Function<C, B>}.
   *
   * @param parameter The parameter to test
   * @return true if the paramater a {@code Function<C, B>}
   */
  private boolean isFunctionCB(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(Function.class)
        && getTypeArgName(parameter, 0).equals("C")
        && getTypeArgName(parameter, 1).equals("B");
  }

  /**
   * Convenience method for extracting the nth type argument.
   *
   * @param parameter The parameter from which to extract a type argument
   * @param index The index of the type argument to extract
   * @return the string name of the type argument to extract
   */
  private String getTypeArgName(final Parameter parameter, final int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  /**
   * Resolves the given parameter.
   *
   * @param parameter the parameter to resolve
   * @param target the object to resolve it to
   * @param extensionContext the extensionContext
   * @return the new value for the given parameter
   */
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
    } else {
      return null;
    }
  }
}
