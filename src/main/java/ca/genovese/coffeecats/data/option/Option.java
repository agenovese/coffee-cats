package ca.genovese.coffeecats.data.option;

import ca.genovese.coffeecats.kind.Kind;

import java.util.Iterator;

/**
 * Represents optional values.
 *
 * Instances of Option are either an instance of Some or the singleton object None.
 * The most idiomatic way to use an Option instance is to treat it as a collection
 * or monad and use map,flatMap, filter, or foreach:
 *
 * @param <A> The type of the item in the option
 */
public interface Option<A> extends Kind<Option, A>, Iterable<A> {

  /**
   * An Option factory which creates Some(x) if the argument is not null,
   * and None if it is null.
   *
   * @param a   The item to make an Option from
   * @param <A> The type of the item to make an Option from
   * @return None if the argument is null, Some(x) otherwise
   */
  static <A> Option<A> of(final A a) {
    return a == null ? none() : some(a);
  }

  /**
   * An Option factory which creates Some(x).
   *
   * @param a   The item to make an Option from
   * @param <A> The type of the item to make an Option from
   * @return Some(x)
   */
  static <A> Option<A> some(final A a) {
    return new Some<>(a);
  }

  /**
   * An Option factory which returns None.
   *
   * @param <A> The type argument for the created Option
   * @return None
   */
  @SuppressWarnings("unchecked")
  static <A> Option<A> none() {
    return (None<A>) None.none();
  }

  /**
   * isDefined returns true if the Option is a Some, false if it is a None.
   *
   * @return true if the Option is a Some, false if it is a None
   */
  boolean isDefined();

  /**
   * Returns the value contained in the Option in the case of a Some,
   * or NoSuchElementException in the case of a None.
   *
   * @return the contained value, or throws a NoSuchElementException
   */
  A get();

  /**
   * Returns the value contained in the Option in the case of a Some,
   * or the provided default value in the case of a None.
   *
   * @param a the default value to return in the case of a None
   * @return the value contained in the Option in the case of a Some,
   * or the provided default value in the case of a None
   */
  default A getOrElse(final A a) {
    return isDefined() ? get() : a;
  }

  /**
   * An Iterator which treats this Option as a collection of up to one element.
   *
   * @return an Iterator over this option
   */
  default Iterator<A> iterator() {
    return new OptionIterator<>(this);
  }
}

