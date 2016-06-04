package ca.genovese.coffeecats.data.list;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

@ToString
@EqualsAndHashCode
final class Nil<A> implements List<A> {
  /**
   * Selects the first element of this List.
   * @return the first element of this List
   */
  public A getHead() {
    throw new NoSuchElementException("getHead on an empty list");
  }

  /**
   * Selects all elements except the first.
   * @return all elements except the first.
   */
  public List<A> getTail() {
    throw new NoSuchElementException("getTail on an empty list");
  }

  /**
   * Tests whether this List is empty
   * @return true if this List is empty, false otherwise
   */
  public boolean isEmpty() {
    return true;
  }

}
