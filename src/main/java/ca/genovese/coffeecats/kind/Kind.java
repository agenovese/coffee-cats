package ca.genovese.coffeecats.kind;

public interface Kind<F, A> {
  @SuppressWarnings("unchecked")
  default F getRealType() {
    return (F) this;
  }
}
