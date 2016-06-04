package ca.genovese.coffeecats.data.list;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;

/**
 * A non empty list characterized by a head and a tail.
 *
 * @param <A> The type of the items in the list
 */
@ToString
@EqualsAndHashCode
final class Cons<A> implements List<A> {
  /**
   * The first item in the list.
   */
  private final A head;
  /**
   * The rest of the items in the list.
   */
  private final List<A> tail;

  /**
   * Constructs a List.
   *
   * @param head The first item in the new List
   * @param tail The rest of the items in the list
   */
  Cons(final A head, final List<A> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Selects the first element of this List.
   *
   * @return the first element of this List
   */
  public A getHead() {
    return head;
  }

  /**
   * Selects all elements except the first.
   *
   * @return all elements except the first.
   */
  public List<A> getTail() {
    return tail;
  }

  /**
   * Tests whether this List is empty.
   *
   * @return true if this List is empty, false otherwise
   */
  public boolean isEmpty() {
    return false;
  }
}
