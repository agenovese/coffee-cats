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


  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * <p>The {@code equals} method implements an equivalence relation
   * on non-null object references:
   * <ul>
   * <li>It is <i>reflexive</i>: for any non-null reference value
   * {@code x}, {@code x.equals(x)} should return
   * {@code true}.
   * <li>It is <i>symmetric</i>: for any non-null reference values
   * {@code x} and {@code y}, {@code x.equals(y)}
   * should return {@code true} if and only if
   * {@code y.equals(x)} returns {@code true}.
   * <li>It is <i>transitive</i>: for any non-null reference values
   * {@code x}, {@code y}, and {@code z}, if
   * {@code x.equals(y)} returns {@code true} and
   * {@code y.equals(z)} returns {@code true}, then
   * {@code x.equals(z)} should return {@code true}.
   * <li>It is <i>consistent</i>: for any non-null reference values
   * {@code x} and {@code y}, multiple invocations of
   * {@code x.equals(y)} consistently return {@code true}
   * or consistently return {@code false}, provided no
   * information used in {@code equals} comparisons on the
   * objects is modified.
   * <li>For any non-null reference value {@code x},
   * {@code x.equals(null)} should return {@code false}.
   * </ul>
   *
   * <p>The {@code equals} method for class {@code Object} implements
   * the most discriminating possible equivalence relation on objects;
   * that is, for any non-null reference values {@code x} and
   * {@code y}, this method returns {@code true} if and only
   * if {@code x} and {@code y} refer to the same object
   * ({@code x == y} has the value {@code true}).
   *
   * <p>Note that it is generally necessary to override the {@code hashCode}
   * method whenever this method is overridden, so as to maintain the
   * general contract for the {@code hashCode} method, which states
   * that equal objects must have equal hash codes.
   *
   * @param o the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   * @see #hashCode()
   * @see java.util.HashMap
   */
  @Override
  public boolean equals(final Object o) {
    return equalsCheck(o);
  }

  /**
   * Returns a hash code value for the object. This method is
   * supported for the benefit of hash tables such as those provided by
   * {@link java.util.HashMap}.
   *
   * <p>The general contract of {@code hashCode} is:
   * <ul>
   * <li>Whenever it is invoked on the same object more than once during
   *     an execution of a Java application, the {@code hashCode} method
   *     must consistently return the same integer, provided no information
   *     used in {@code equals} comparisons on the object is modified.
   *     This integer need not remain consistent from one execution of an
   *     application to another execution of the same application.
   * <li>If two objects are equal according to the {@code equals(Object)}
   *     method, then calling the {@code hashCode} method on each of
   *     the two objects must produce the same integer result.
   * <li>It is <em>not</em> required that if two objects are unequal
   *     according to the {@link java.lang.Object#equals(java.lang.Object)}
   *     method, then calling the {@code hashCode} method on each of the
   *     two objects must produce distinct integer results.  However, the
   *     programmer should be aware that producing distinct integer results
   *     for unequal objects may improve the performance of hash tables.
   * </ul>
   *
   * <p>As much as is reasonably practical, the hashCode method defined by
   * class {@code Object} does return distinct integers for distinct
   * objects. (This is typically implemented by converting the internal
   * address of the object into an integer, but this implementation
   * technique is not required by the
   * Java&trade; programming language.)
   *
   * @return  a hash code value for this object.
   * @see     java.lang.Object#equals(java.lang.Object)
   * @see     java.lang.System#identityHashCode
   */
  @Override
  public int hashCode() {
    return hashCodeGen();
  }
}
