package ca.genovese.coffeecats.data.option;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by angelo on 2016-06-04.
 */
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
