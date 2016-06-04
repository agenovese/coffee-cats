package ca.genovese.coffeecats.data.list;

import ca.genovese.coffeecats.kind.Kind;

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

