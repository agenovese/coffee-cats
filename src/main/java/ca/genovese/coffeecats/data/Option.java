package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.Public;
import ca.genovese.coffeecats.kind.Kind;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static ca.genovese.coffeecats.data.None.NONE;

@Public
public interface Option<A> extends Kind<Option, A>, Iterable<A> {
  @Public
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

  @Public
  default A getOrElse(A a) {
    return isDefined() ? get() : a;
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

  @Override
  public Iterator<A> iterator() {
    return new Iterator<A>() {
      boolean next = true;
      @Override
      public boolean hasNext() {
        return next;
      }

      @Override
      public A next() {
        if(next) {
          next = false;
          return a;
        } else {
          throw new NoSuchElementException();
        }
      }
    };
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
    throw new UnsupportedOperationException("get() called on a None");
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