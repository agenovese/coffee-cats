package ca.genovese.coffeecats.data;

/**
 * A "void" type
 */
public final class Unit {
  /**
   * The single instance of unit, allowing for == comparisons
   */
  public static final Unit unit = new Unit();

  /**
   * construct a unit value
   */
  private Unit() {
  }
}
