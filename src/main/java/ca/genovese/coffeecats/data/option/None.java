package ca.genovese.coffeecats.data.option;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.NoSuchElementException;

/**
 * Created by angelo on 2016-06-04.
 */
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
