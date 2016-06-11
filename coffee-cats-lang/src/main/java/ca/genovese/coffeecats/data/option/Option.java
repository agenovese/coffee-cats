package ca.genovese.coffeecats.data.option;

import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents optional values.
 *
 * <p>Instances of Option are either an instance of Some or the singleton object None.
 * The most idiomatic way to use an Option instance is to treat it as a collection
 * or monad and use map,flatMap, filter, or foreach:
 *
 * @param <A> The type of the item in the option
 */
public abstract class Option<A> implements Kind<Option, A>, Iterable<A> {

  /**
   * Make this abstract class sealed by having a private constructor
   */
  private Option() {

  }

  /**
   * An Option factory which creates Some(x) if the argument is not null,
   * and None if it is null.
   *
   * @param a   The item to make an Option from
   * @param <A> The type of the item to make an Option from
   * @return None if the argument is null, Some(x) otherwise
   */
  public static <A> Option<A> of(final A a) {
    return a == null ? none() : some(a);
  }

  /**
   * An Option factory which creates Some(x).
   *
   * @param a   The item to make an Option from
   * @param <A> The type of the item to make an Option from
   * @return Some(x)
   */
  public static <A> Option<A> some(final A a) {
    return new Some<>(a);
  }

  /**
   * An Option factory which returns None.
   *
   * @param <A> The type argument for the created Option
   * @return None
   */
  @SuppressWarnings("unchecked")
  public static <A> Option<A> none() {
    return (None<A>) None.NONE;
  }

  /**
   * isDefined returns true if the Option is a Some, false if it is a None.
   *
   * @return true if the Option is a Some, false if it is a None
   */
  public abstract boolean isDefined();

  /**
   * Returns the value contained in the Option in the case of a Some,
   * or NoSuchElementException in the case of a None.
   *
   * @return the contained value, or throws a NoSuchElementException
   */
  public abstract A get();

  /**
   * Returns the value contained in the Option in the case of a Some,
   * or the provided default value in the case of a None.
   *
   * @param a the default value to return in the case of a None
   * @return the value contained in the Option in the case of a Some,
   *     or the provided default value in the case of a None
   */
  public A getOrElse(final A a) {
    return isDefined() ? get() : a;
  }

  /**
   * An Iterator which treats this Option as a collection of up to one element.
   *
   * @return an Iterator over this option
   */
  public Iterator<A> iterator() {
    return new OptionIterator<>(this);
  }

  /**
   * This singleton object represents non-existent values.
   *
   * @param <A> The type of value in the Option
   */
  @ToString
  @EqualsAndHashCode
  private final static class None<A> extends Option<A> {
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

  /**
   * Class Some&gt;A&lt; represents existing values of type A.
   *
   * @param <A> The type of the contained value
   */
  @ToString
  @EqualsAndHashCode
  private static final class Some<A> extends Option<A> {
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

}

