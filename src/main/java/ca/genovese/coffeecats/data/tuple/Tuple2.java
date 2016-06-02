package ca.genovese.coffeecats.data.tuple;

import lombok.EqualsAndHashCode;

import java.util.function.BiFunction;

@EqualsAndHashCode
public class Tuple2<A, B> {
  public final A _1;
  public final B _2;

  public Tuple2(A a, B b) {
    _1 = a;
    _2 = b;
  }

  public <R> R applyTo(BiFunction<A,B,R> f) {
    return f.apply(_1, _2);
  }
}
