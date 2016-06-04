package ca.genovese.coffeecats.data.option;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.NoSuchElementException;

@ToString
@EqualsAndHashCode
final class None<A> implements Option<A> {
  private static final None NONE = new None();

  @SuppressWarnings("unchecked")
  static <A> None<A> none() {
    return NONE;
  }

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
