package ca.genovese.coffeecats.data.option;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Class Some&gt;A&lt; represents existing values of type A.
 *
 * @param <A> The type of the contained value
 */
@ToString
@EqualsAndHashCode
final class Some<A> implements Option<A> {
  /**
   * The contained value.
   */
  private final A value;

  /**
   * Constructs a Some containing the provided value.
   *
   * @param value The value to be contained in the constructed Option.
   */
  Some(final A value) {
    this.value = value;
  }

  /**
   * Returns true if the option is an instance of Some, false otherwise.
   *
   * @return True if the option is an instance of Some, false otherwise.
   */
  @Override
  public boolean isDefined() {
    return true;
  }

  /**
   * Returns the option's value.
   *
   * @return The option's value.
   */
  @Override
  public A get() {
    return value;
  }
}
