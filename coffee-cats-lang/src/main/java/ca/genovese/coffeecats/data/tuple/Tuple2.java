package ca.genovese.coffeecats.data.tuple;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.BiFunction;

/**
 * A tuple of 2 elements.
 *
 * @param <A> The type of the first element
 * @param <B> The type of the second element
 */
@ToString
@EqualsAndHashCode
public final class Tuple2<A, B> {
  /**
   * The first Element.
   */
  private final A a;

  /**
   * The first Element.
   */
  private final B b;

  /**
   * Construct a tuple.
   *
   * @param a The first element in the Tuple
   * @param b The second element in the Tuple
   */
  public Tuple2(final A a, final B b) {
    this.a = a;
    this.b = b;
  }

  /**
   * Get the first element in the Tuple.
   *
   * @return the first element in the Tuple
   */
  public A getA() {
    return a;
  }

  /**
   * Get the second element in the Tuple.
   *
   * @return the second element in the Tuple
   */
  public B getB() {
    return b;
  }

  /**
   * Apply a 2 argument function to the tuple.
   *
   * @param f the 2 argument function to apply with the tuple's elements as arguments.
   * @param <R> the return type of the provided function
   * @return the result of the function
   */
  public <R> R applyTo(final BiFunction<A, B, R> f) {
    return f.apply(a, b);
  }
}
