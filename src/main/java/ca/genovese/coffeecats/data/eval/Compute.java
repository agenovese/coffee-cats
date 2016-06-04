package ca.genovese.coffeecats.data.eval;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Compute is a type of Eval&lt;A&gt; that is used to chain computations
 * involving map() and flatMap(). Along with Eval#flatMap it
 * implements the trampoline that guarantees stack-safety.
 *
 * <p>Users should not instantiate Compute instances
 * themselves. Instead, they will be automatically created when
 * needed.
 *
 * <p>Unlike a traditional trampoline, the internal workings of the
 * trampoline are not exposed. This allows a slightly more efficient
 * implementation of the .value method.
 *
 * @param <A> The type returned by this Eval
 */
final class Compute<A> implements Eval<A> {
  /**
   * The function which returns the initial Eval.
   */
  private final Supplier<Eval> start;
  /**
   * The function to apply to start's value to calculate the value of this eval.
   */
  private final Function run;

  /**
   * Creates a new Eval, based on an existing Eval and a function.
   *
   * @param start The Eval from which this Eval's computation is started
   * @param run The function to apply to the start Eval's value to calculate this Eval
   */
  Compute(final Supplier<Eval> start, final Function run) {
    this.start = start;
    this.run = run;
  }

  /**
   * Lazily perform a computation based on an Eval&lt;A&gt;, using the
   * function `f` to produce an Eval&lt;B&gt; given an A.
   *
   * <p>This call is stack-safe -- many .flatMap calls may be chained
   * without consumed additional stack during evaluation. It is also
   * written to avoid left-association problems, so that repeated
   * calls to .flatMap will be efficiently applied.
   *
   * <p>Computation performed in f is always lazy, even when called on an
   * eager (Now) instance.
   *
   * @param f   the function to apply to the result of the current computation
   * @param <B> output type of the computation returned by the applied function
   * @return A new computation which includes the application of f
   */
  @Override
  @SuppressWarnings("unchecked")
  public <B> Eval<B> flatMap(final Function<A, Eval<B>> f) {
    return new Compute<>(start,
        s -> new Compute<>(() -> (Eval) this.run.apply(s), f));
  }

  /**
   * Ensure that the result of the computation (if any) will be
   * memoized.
   *
   * <p>Practically, this means that when called on an Always&lt;A&gt; a
   * Later&lt;A&gt; with an equivalent computation will be returned.
   *
   * @return A new, memoizing, Eval that is equivalent to the current Eval
   */
  @Override
  public Eval<A> memoize() {
    return new Later<>(this::value);
  }

  /**
   * Evaluate the computation and return an A value.
   *
   * <p>For lazy instances (Later, Always), any necessary computation
   * will be performed at this point. For eager instances (Now), a
   * value will be immediately returned.
   *
   * @return The result of the computation
   */
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
