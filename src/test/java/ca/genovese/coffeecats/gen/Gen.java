package ca.genovese.coffeecats.gen;

import ca.genovese.coffeecats.data.list.List;
import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.data.tuple.Tuple2;
import ca.genovese.coffeecats.kind.Kind;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface Gen<A> extends Kind<Gen, A> {
  Gen<Integer> intGen = FunctionalRandom::nextInt;

  Tuple2<A, FunctionalRandom> run(FunctionalRandom rnd);

  static <A> Gen<A> unit(A a) {
    return (r) -> new Tuple2<>(a, r);
  }

  default <B> Gen<B> map(Function<A, B> f) {
    return (r) -> run(r).applyTo((a, rnd) -> new Tuple2<>(f.apply(a), rnd));
  }

  default <B> Gen<B> flatMap(Function<A, Gen<B>> f) {
    return (r) -> run(r).applyTo((a, rnd) -> f.apply(a).run(rnd));
  }

  default <B> Gen<B> nTimes(int n, Function<A, B> f, BinaryOperator<B> c) {
    Gen<B> result = map(f);

    for (int i = 1; i < n; i++) {
      result = result.flatMap((b) -> map(a -> c.apply(b, f.apply(a))));
    }

    return result;
  }

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
