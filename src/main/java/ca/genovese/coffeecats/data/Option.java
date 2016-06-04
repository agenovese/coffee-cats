package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static ca.genovese.coffeecats.data.None.NONE;

public interface Option<A> extends Kind<Option, A>, Iterable<A> {

  static <A> Option<A> of(final A a) {
    return a == null ? none() : some(a);
  }

  static <A> Option<A> some(final A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  static <A> Option<A> none() {
    return (None<A>) NONE;
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

@ToString
@EqualsAndHashCode
final class Some<A> implements Option<A> {
  private final A value;

  Some(final A value) {
    this.value = value;
  }

  @Override
  public boolean isDefined() {
    return true;
  }

  @Override
  public A get() {
    return value;
  }
}

@ToString
@EqualsAndHashCode
final class None<A> implements Option<A> {
  static final None NONE = new None();

  private None() {

  }

  @Override
  public boolean isDefined() {
    return false;
  }

  @Override
  public A get() {
    throw new NoSuchElementException("get() called on a None");
  }
}

final class OptionIterator<A> implements Iterator<A> {
  private final Option<A> opt;
  private boolean hasNext;

  OptionIterator(final Option<A> opt) {
    this.opt = opt;
    hasNext = opt.isDefined();
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }

  @Override
  public A next() {
    if (hasNext) {
      hasNext = false;
      return opt.get();
    } else {
      throw new NoSuchElementException();
    }
  }
}
