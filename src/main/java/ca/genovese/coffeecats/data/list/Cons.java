package ca.genovese.coffeecats.data.list;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;

/**
 * Created by angelo on 2016-06-04.
 */
@ToString
@EqualsAndHashCode
final class Cons<A> implements List<A> {
  private final A head;
  private final List<A> tail;

  Cons(final A head, final List<A> tail) {
    this.head = head;
    this.tail = tail;
  }

  public A getHead() {
    return head;
  }

  public List<A> getTail() {
    return tail;
  }

  @Override
  public Iterator<A> iterator() {
    return new ListIterator<>(this);
  }

}
