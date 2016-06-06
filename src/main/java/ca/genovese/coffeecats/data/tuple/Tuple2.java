package ca.genovese.coffeecats.data.tuple;

import lombok.Data;

import java.util.function.BiFunction;

/**
 * A tuple of 2 elements.
 *
 * @param <A> The type of the first element
 * @param <B> The type of the second element
 */
@Data
public final class Tuple2<A, B> {
  /**
   * The first Element.
   */
  private final A _1;

  /**
   * The first Element.
   */
  private final B _2;

  /**
   * Construct a tuple.
   */
  public Tuple2(A a, B b) {
    _1 = a;
    _2 = b;
  }

  /**
   * Apply a 2 argument function to the tuple.
   */
  public <R> R applyTo(final BiFunction<A, B, R> f) {
    return f.apply(_1, _2);
  }
}
