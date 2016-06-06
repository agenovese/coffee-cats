package ca.genovese.coffeecats.data.option;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.NoSuchElementException;

/**
 * This singleton object represents non-existent values.
 *
 * @param <A> The type of value in the Option
 */
@ToString
@EqualsAndHashCode
final class None<A> implements Option<A> {
  /**
   * The singleton instance.
   */
  private static final None NONE = new None();

  /**
   * No action constructor.
   */
  private None() {

  }

  /**
   * A method for getting the singleton instance, parameterized by the type of Option required.
   *
   * @param <A> The type of Option
   * @return An Instance of None
   */
  @SuppressWarnings("unchecked")
  static <A> None<A> none() {
    return NONE;
  }

  /**
   * Returns whether or not the current item is defined.
   *
   * @return false
   */
  @Override
  public boolean isDefined() {
    return false;
  }

  /**
   * Returns the contained item, or throws a NoSuchElementException if the item is undefined.
   *
   * @return throws a NoSuchElementException
   */
  @Override
  public A get() {
    throw new NoSuchElementException("get() called on a None");
  }
}
