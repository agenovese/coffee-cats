package ca.genovese.coffeecats.data.option;

import ca.genovese.coffeecats.kind.Kind;

import java.util.Iterator;

public interface Option<A> extends Kind<Option, A>, Iterable<A> {

  static <A> Option<A> of(final A a) {
    return a == null ? none() : some(a);
  }

  static <A> Option<A> some(final A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  static <A> Option<A> none() {
    return (None<A>) None.none();
  }

  boolean isDefined();

  A get();

  default A getOrElse(final A a) {
    return isDefined() ? get() : a;
  }

  default Iterator<A> iterator() {
    return new OptionIterator<>(this);
  }
}

