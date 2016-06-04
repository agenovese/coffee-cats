package ca.genovese.coffeecats.data.list;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by angelo on 2016-06-04.
 */
@ToString
@EqualsAndHashCode
final class Nil<A> implements List<A> {
  public A getHead() {
    throw new NoSuchElementException("getHead on an empty list");
  }

  public List<A> getTail() {
    throw new NoSuchElementException("getTail on an empty list");
  }

  @Override
  public Iterator<A> iterator() {
    return new ListIterator<>(this);
  }
}
