package ca.genovese.coffeecats.data.eval;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Compute is a type of Eval&lt;A&gt; that is used to chain computations
 * involving .map and .flatMap. Along with Eval#flatMap it
 * implements the trampoline that guarantees stack-safety.
 * <p>
 * Users should not instantiate Compute instances
 * themselves. Instead, they will be automatically created when
 * needed.
 * <p>
 * Unlike a traditional trampoline, the internal workings of the
 * trampoline are not exposed. This allows a slightly more efficient
 * implementation of the .value method.
 */
final class Compute<A> implements Eval<A> {
  private final Supplier<Eval> start;
  private final Function run;

  Compute(final Supplier<Eval> start, final Function run) {
    this.start = start;
    this.run = run;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <B> Eval<B> flatMap(final Function<A, Eval<B>> f) {
    return new Compute<>(start,
        s -> new Compute<>(() -> (Eval) this.run.apply(s), f));
  }

  @Override
  public Eval<A> memoize() {
    return new Later<>(this::value);
  }

  @SuppressWarnings("unchecked")
  public A value() {
    final LinkedList<Function> fs = new LinkedList<>();
    Eval curr = this;
    boolean cont = true;

    while (cont) {
      if (curr instanceof Compute) {
        final Compute c = (Compute) curr;
        final Eval cstart = (Eval) c.start.get();
        if (cstart instanceof Compute) {
          fs.add(0, c.run);
          curr = cstart;
        } else {
          curr = (Eval) c.run.apply(cstart.value());
        }
      } else {
        if (fs.isEmpty()) {
          cont = false;
        } else {
          final Function f = fs.pop();
          curr = (Eval) f.apply(curr.value());
        }
      }
    }
    return (A) curr.value();
  }
}
