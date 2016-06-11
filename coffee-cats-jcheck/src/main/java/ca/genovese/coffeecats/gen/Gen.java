package ca.genovese.coffeecats.gen;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.data.tuple.Tuple2;
import ca.genovese.coffeecats.kind.Kind;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A random value generator, used for creating property based tests.
 *
 * <p>Gen is actually a specialized implementation of the State Monad
 * {@code (FunctionalRandom) -> Tuple2<FunctionalRandom, A>}
 *
 * @param <A> The type of value returned by this Gen
 */
@FunctionalInterface
public interface Gen<A> extends Kind<Gen, A> {
  /**
   * A {@code Gen<Integer>} used as a basis for most other Gen values.
   */
  Gen<Integer> intGen = FunctionalRandom::nextInt;

  /**
   * A method which, given a FunctionalRandom, produces the next value for this Gen.
   *
   * @param rnd A FunctionalRandom
   * @return the next result for this Gen
   */
  Tuple2<FunctionalRandom, A> run(FunctionalRandom rnd);

  /**
   * Method which produces a Gen returning a fixed value.
   *
   * @param a value returned by this Gen
   * @param <A> type of value returned by this Gen
   * @return a
   */
  static <A> Gen<A> unit(A a) {
    return (r) -> new Tuple2<>(r, a);
  }

  /**
   * Produce a new Gen which returns the results of applying a Function to the values returned by this Gen.
   *
   * @param f Function to apply
   * @param <B> return type
   * @return a new {@code Gen<B>}
   */
  default <B> Gen<B> map(Function<A, B> f) {
    return (r) -> run(r).applyTo((rnd, a) -> new Tuple2<>(rnd, f.apply(a)));
  }

  /**
   * flatmap.
   * @param f Function to apply
   * @param <B> return type
   * @return a new {@code Gen<B>}
   */
  default <B> Gen<B> flatMap(Function<A, Gen<B>> f) {
    return (r) -> run(r).applyTo((rnd, a) -> f.apply(a).run(rnd));
  }

  /**
   * return a new Gen that returns the result of calling the current Gen a given number of times
   * converting the results using a givand combining the results using the given BinaryOperator.
   *
   * @param n the number of runs of this Gen to combine
   * @param f the transformation function
   * @param c the combination function
   * @param <B> The output type
   * @return The combined result
   */
  default <B> Gen<B> nTimes(int n, Function<A, B> f, BinaryOperator<B> c) {
    Gen<B> result = map(f);

    for (int i = 1; i < n; i++) {
      result = result.flatMap((b) -> map(a -> c.apply(b, f.apply(a))));
    }

    return result;
  }

  /**
   * Utility function for creating a property check.
   *
   * @param check property to check
   * @return List of None or an Exception for each value checked
   */
  default Gen<List<Option<Exception>>> createCheck(Consumer<A> check) {
    return map((a) -> {
      try {
        check.accept(a);
      } catch (Exception e) {
        return List.of(Option.of(e));
      }
      return List.of(Option.<Exception>none());
    }).nTimes(10, l -> l, (a, b) -> b.append(a));
  }
}
