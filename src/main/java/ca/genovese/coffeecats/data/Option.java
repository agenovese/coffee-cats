package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static ca.genovese.coffeecats.data.None.NONE;

public interface Option<A> extends Kind<Option, A>, Iterable<A> {
  
  static <A> Option<A> of(A a) {
    return a == null ? NONE : some(a);
  }

  static <A> Option<A> some(A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  static <A> Option<A> none() {
    return (None<A>) NONE;
  }

  boolean isDefined();

  A get();

  default A getOrElse(A a) {
    return isDefined() ? get() : a;
  }

  default Iterator<A> iterator() {
    return new OptionIterator<>(this);
  }
}

@ToString
@EqualsAndHashCode
class Some<A> implements Option<A> {
  private final A a;

  Some(A a) {
    this.a = a;
  }

  @Override
  public boolean isDefined() {
    return true;
  }

  @Override
  public A get() {
    return a;
  }
}

@ToString
@EqualsAndHashCode
class None<A> implements Option<A> {
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

class OptionIterator<A> implements Iterator<A> {
  private final Option<A> opt;
  private boolean hasNext;

  OptionIterator(Option<A> opt) {
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