package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface List<A> extends Iterable<A>, Kind<List, A> {
  @SafeVarargs
  static <A> List<A> of(final A... as) {
    List<A> list = new Nil<>();

    for (int i = as.length - 1; i >= 0; i--) {
      list = new Cons<>(as[i], list);
    }

    return list;
  }

  static <A> List<A> cons(final A a, final List<A> as) {
    return new Cons<>(a, as);
  }

  @SuppressWarnings("unused")
  default int length() {
    int length = 0;

    for (A a : this) {
      length++;
    }

    return length;
  }

  default List<A> reverse() {
    List<A> result = of();

    for (A a : this) {
      result = cons(a, result);
    }

    return result;
  }

  default List<A> append(final List<A> as) {
    List<A> result = as;

    for (A a : reverse()) {
      result = cons(a, result);
    }

    return result;
  }

  A getHead();

  List<A> getTail();
}

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
