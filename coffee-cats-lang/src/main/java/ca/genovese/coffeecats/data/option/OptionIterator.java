package ca.genovese.coffeecats.data.option;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * * An iterator over an Option.
 *
 * @param <A> The type of the items in this iteration
 * @see Option
 * @see Iterable
 * @see Iterator
 */
final class OptionIterator<A> implements Iterator<A> {
  /**
   * The option over which this iterator iterates.
   */
  private final Option<A> opt;

  /**
   * A boolean representing whether or not this iteration
   * has gone past the single item in the Option.
   */
  private boolean hasNext;

  /**
   * Constructs an Iterator over the provided option Option.
   *
   * @param opt The
   */
  OptionIterator(final Option<A> opt) {
    this.opt = opt;
    hasNext = opt.isDefined();
  }

  /**
   * Returns {@code true} if the iteration has more elements.
   * (In other words, returns {@code true} if {@link #next} would
   * return an element rather than throwing an exception.)
   *
   * @return {@code true} if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    return hasNext;
  }

  /**
   * Returns the next element in the iteration.
   *
   * @return the next element in the iteration
   * @throws NoSuchElementException if the iteration has no more elements
   */
  @Override
  public A next() {
    if (hasNext) {
      hasNext = false;
      return opt.get();
    } else {
      throw new NoSuchElementException();
    }
  }
}
