package ca.genovese.coffeecats.data.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator over a List.
 *
 * @param <A> The type of the items in this
 * @see List
 * @see Iterable
 * @see Iterator
 */
final class ListIterator<A> implements Iterator<A> {
  /**
   * The rest of the list.
   */
  private List<A> list;

  /**
   * Construct a new Iterator.
   *
   * @param list the List over which this will Iterate
   */
  ListIterator(final List<A> list) {
    this.list = list;
  }

  /**
   * Returns {@code true} if the iteration has more elements.
   * (In other words, returns {@code true} if {@link #next} would
   * return an element rather than throwing an exception.)
   *
   * @return {@code true} if the iteration has more elements, false otherwise
   */
  @Override
  public boolean hasNext() {
    return !(list instanceof Nil);
  }

  /**
   * Returns the next element in the iteration.
   *
   * @return the next element in the iteration
   * @throws NoSuchElementException if the iteration has no more elements
   */
  @Override
  public A next() {
    if (list.isEmpty()) {
      throw new NoSuchElementException();
    } else {
      final A head = list.getHead();
      list = list.getTail();
      return head;
    }
  }
}
