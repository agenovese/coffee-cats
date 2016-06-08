package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.kind.Kind;
import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * Abstract class for ParameterResolvers providing instances for Law Tests.
 */
public abstract class AbstractInstanceProvider<S, F> implements ParameterResolver {
  /**
   * provide an instance of the available structures.
   *
   * @return an implementation of the structure interfaces for the given class
   */
  protected abstract S instance();

  /**
   * provide an instance of the given class.
   *
   * @return an instance of the given class
   */
  protected abstract F kind();

  /**
   * return the type of an implementation of the available structures.
   *
   * @return the class of an implementation of the structure interfaces for the given class
   */
  protected abstract Class<S> type();

  /**
   * Determine if this resolver supports resolution of the given {@link Parameter}
   * for the supplied {@code target} and {@link ExtensionContext}.
   *
   * <p>The {@link java.lang.reflect.Method} or {@link java.lang.reflect.Constructor}
   * in which the {@code parameter} is declared can be retrieved via
   * {@link Parameter#getDeclaringExecutable()}.
   *
   * @param parameter the parameter to be resolved
   * @param target the container for the target on which the {@code java.lang.reflect.Executable}
   * will be invoked; may be <em>empty</em> if the {@code Executable} is a constructor
   * or {@code static} method
   * @param extensionContext the extension context for the {@code Executable}
   * about to be invoked
   * @return {@code true} if this resolver can resolve the parameter
   * @see #resolve
   * @see java.lang.reflect.Parameter
   * @see java.lang.reflect.Executable
   * @see java.lang.reflect.Method
   * @see java.lang.reflect.Constructor
   */
  @Override
  public final boolean supports(final Parameter parameter,
                          final Optional<Object> target,
                          final ExtensionContext extensionContext) {
    return isInstance(parameter) || isKind(parameter);
  }

  /**
   * Determine if the parameter is of the given Class.
   *
   * @param parameter the parameter to be resolved
   * @return true if the parameter is of the correct type
   */
  private boolean isKind(final Parameter parameter) {
    return parameter.getType().equals(Kind.class)
        && getTypeArgName(parameter, 0).equals("F");
  }

  /**
   * Determine if the parameter for an instance of an available structural interface.
   *
   * @param parameter the parameter to be resolved
   * @return true if the parameter is of the correct type
   */
  private boolean isInstance(final Parameter parameter) {
    return parameter.getType().isAssignableFrom(type())
        && getTypeArgName(parameter, 0).equals("F");
  }

  /**
   * extract the name of a type argument from the parameter.
   *
   * @param parameter the parameter to be resolved
   * @param index index of the type argument to extract
   * @return the name of the given type argument as a String
   */
  private String getTypeArgName(final Parameter parameter, final int index) {
    return ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[index].getTypeName();
  }

  /**
   * Resolve the given {@link Parameter} for the supplied {@code target} and
   * {@link ExtensionContext}.
   *
   * <p>The {@link java.lang.reflect.Method} or {@link java.lang.reflect.Constructor}
   * in which the {@code parameter} is declared can be retrieved via
   * {@link Parameter#getDeclaringExecutable()}.
   *
   * @param parameter the parameter to be resolved
   * @param target the container for the target on which the {@code java.lang.reflect.Executable}
   * will be invoked; may be <em>empty</em> if the {@code Executable} is a constructor
   * or {@code static} method
   * @param extensionContext the extension context for the {@code Executable}
   * about to be invoked
   * @return the resolved parameter object
   * @see #supports
   * @see java.lang.reflect.Parameter
   * @see java.lang.reflect.Executable
   * @see java.lang.reflect.Method
   * @see java.lang.reflect.Constructor
   */
  @Override
  @SuppressWarnings("unchecked")
  public final Object resolve(final Parameter parameter,
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
