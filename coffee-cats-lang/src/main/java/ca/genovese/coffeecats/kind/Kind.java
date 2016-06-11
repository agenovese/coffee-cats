package ca.genovese.coffeecats.kind;

/**
 * Interface representing a type with one type argument.
 *
 * @param <F> The type
 * @param <A> The first type argument
 */
public interface Kind<F, A> {
  /**
   * A convenience method for getting out a value casted to the non-generic type.
   *
   * @return the value of this "Kind" as a non-generic type.
   */
  @SuppressWarnings("unchecked")
  default F getRealType() {
    return (F) this;
  }
}
