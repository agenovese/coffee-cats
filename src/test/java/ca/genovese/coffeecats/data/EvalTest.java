package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.data.eval.Eval;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertSame;

/**
 * Test for Eval types.
 */
public final class EvalTest {
  /**
   * Counter for the number of executions.
   * Used to detect lazyness/strictness
   * and memoization of executions.
   */
  private int execCount;

  /**
   * Reset the execCount prior to each test.
   */
  @BeforeEach
  public void resetCount() {
    execCount = 0;
  }

  /**
   * Test that the now Eval is Strict and Memoized.
   */
  @Test
  @DisplayName("Creating a now Eval")
  public void testNowEval() {

    Eval<Integer> i = Eval.now(() -> {
      execCount++;
      return 4;
    });

    assertSame(i, i.memoize());

    i.value();
    i.value();
    i.value();

    assertEquals(1, execCount, "Now Eval's should only execute their supplier on construction");

    i = i.map(x -> {
      execCount++;
      return x + 1;
    });

    assertEquals(1, execCount, "Eval's should execute mapped functions lazily");

    i.value();

    assertEquals(2, execCount, "Eval's should execute mapped function on value access");
  }

  /**
   * Test that the later Eval is Lazy and Memoized.
   */
  @Test
  @DisplayName("Creating a later Eval")
  public void testLaterEval() {

    Eval<Integer> i = Eval.later(() -> {
      execCount++;
      return 4;
    });

    assertSame(i, i.memoize());

    assertEquals(0, execCount, "Later Eval's should only execute their supplier on first access");

    i.value();
    i.value();
    i.value();

    assertEquals(1, execCount, "Later Eval's should only execute their supplier on first access");

    i = i.map(x -> {
      execCount++;
      return x + 1;
    });

    assertEquals(1, execCount, "Eval's should execute mapped functions lazily");

    i.value();

    assertEquals(2, execCount, "Eval's should execute mapped function on value access");
  }

  /**
   * Test that the always Eval is Lazy and not Memoized.
   */
  @Test
  @DisplayName("Creating an always Eval")
  public void testAlwaysEval() {

    Eval<Integer> i = Eval.always(() -> {
      execCount++;
      return 4;
    });

    i.value();
    i.value();
    i.value();

    assertEquals(3, execCount, "Always Eval's should execute their supplier for each value access");

    final Eval<Integer> m = i.memoize();

    m.value();
    m.value();
    m.value();

    assertEquals(4, execCount, "Memoized Always Eval's should behave like a later eval");

    i = i.map(x -> {
      execCount++;
      return x + 1;
    });

    assertEquals(4, execCount, "Eval's should execute mapped functions lazily");

    i.value();

    assertEquals(6, execCount, "Eval's should execute mapped function on value access");

    i = i.memoize();

    assertEquals(5, i.value().intValue());
    assertEquals(5, i.value().intValue());
    assertEquals(5, i.value().intValue());

    // TODO - why is accessing the value of a memoized eval incrementing the counter?
    assertEquals(8, execCount, "WTF?");
  }
}
