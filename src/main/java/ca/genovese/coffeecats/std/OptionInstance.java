package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.Option;
import ca.genovese.coffeecats.kind.Kind;
import ca.genovese.coffeecats.structures.InvariantFunctor;

import java.util.function.Function;

import static ca.genovese.coffeecats.data.Option.none;
import static ca.genovese.coffeecats.data.Option.some;

public class OptionInstance implements InvariantFunctor<Option> {
  @Override
  @SuppressWarnings("unchecked")
  public <A, B> Kind<Option, B> imap(Kind<Option, A> fa, Function<A, B> f, Function<B, A> g) {
    return fa.getRealType().isDefined() ? some(f.apply((A) fa.getRealType().get())) : none();
  }
}
