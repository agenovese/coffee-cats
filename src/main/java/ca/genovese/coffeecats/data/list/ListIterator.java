package ca.genovese.coffeecats.data.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by angelo on 2016-06-04.
 */
final class ListIterator<A> implements Iterator<A> {
  private List<A> list;

  ListIterator(final List<A> list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return !(list instanceof Nil);
  }

  @Override
  public A next() {
    if (list instanceof Cons) {
      final A head = list.getHead();
      list = list.getTail();
      return head;
    } else {
      throw new NoSuchElementException();
    }
  }
}
