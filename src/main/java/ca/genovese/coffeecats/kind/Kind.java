package ca.genovese.coffeecats.kind;

import ca.genovese.coffeecats.Public;

public interface Kind<F, A> {
  @Public
  @SuppressWarnings("unchecked")
  default F getRealType() {
    return (F) this;
  }
}
