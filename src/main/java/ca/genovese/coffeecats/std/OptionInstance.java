package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.CovariantFunctor;

import java.util.function.Function;

import static ca.genovese.coffeecats.data.option.Option.none;
import static ca.genovese.coffeecats.data.option.Option.some;

/**
 * An object which implements all the applicable structures for Option
 */
public final class OptionInstance implements CovariantFunctor<Option> {
  /**
   * A convenience instance of ListInstance.
   */
  public static final OptionInstance optionInstance = new OptionInstance();

  /**
   * An Implementation of Map for Option.
   *
   * @param fa The starting Option
   * @param f The function to map over fa
   * @param <A> The input type
   * @param <B> The output type
   * @return An Option which is None if fa was None or Some(f.apply(fa.get())) if fa was a Some
   */
  @Override
  @SuppressWarnings("unchecked")
  public <A, B> Kind<Option, B> map(final Kind<Option, A> fa, final Function<A, B> f) {
    return fa.getRealType().isDefined() ? some(f.apply((A) fa.getRealType().get())) : none();
  }
}
