package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface List<A> extends Iterable<A>, Kind<List, A> {
  @SafeVarargs
  static <A> List<A> create(A... as) {
    List<A> list = new Nil<>();

    for (int i = as.length - 1; i >= 0; i--) {
      list = new Cons<>(as[i], list);
    }

    return list;
  }

  static <A> List<A> cons(A a, List<A> as) {
    return new Cons<>(a, as);
  }

  @SuppressWarnings("unused")
  default int length() {
    Eval<Integer> length = Eval.now(0);

    for (A a : this) {
      length = length.flatMap(i -> Eval.later(() -> i + 1));
     }

    return length.value();
  }

  A getHead();

  List<A> getTail();
}

@ToString
@EqualsAndHashCode
final class Cons<A> implements List<A> {
  private final A head;
  private final List<A> tail;

  Cons(A head, List<A> tail) {
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

  ListIterator(List<A> list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return !(list instanceof Nil);
  }

  @Override
  public A next() {
    if (list instanceof Cons) {
      A head = list.getHead();
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
