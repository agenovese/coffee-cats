package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.Public;
import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Public
public interface List<A> extends Iterable<A>, Kind<List, A> {
  @Public
  @SuppressWarnings("unchecked")
  static <A> List<A> create(A... as) {
    List list = new Nil<>();

    for (int i = as.length - 1; i >= 0; i--) {
      list = new Cons<>(as[i], list);
    }

    return list;
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
    if(list instanceof Cons) {
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
    throw new IllegalStateException("Head on an empty list");
  }

  public List<A> getTail() {
    throw new IllegalStateException("Tail on an empty list");
  }

  @Override
  public Iterator<A> iterator() {
    return new Iterator<A>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public A next() {
        throw new NoSuchElementException();
      }
    };
  }
}