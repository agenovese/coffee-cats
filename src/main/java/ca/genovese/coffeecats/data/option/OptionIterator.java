package ca.genovese.coffeecats.data.option;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by angelo on 2016-06-04.
 */
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
