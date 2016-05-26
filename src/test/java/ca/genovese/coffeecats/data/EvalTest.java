package ca.genovese.coffeecats.data;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

public class EvalTest {
  int execCount;

  @BeforeEach
  public void resetCount() {
    execCount = 0;
  }

  @Test
  @DisplayName("Creating a now Eval")
  public void testNowEval() {

    Eval<Integer> i = Eval.now(() -> {execCount++; return 4;});

    i.value();
    i.value();
    i.value();

    assertEquals(1, execCount);

    i = i.map(x -> {execCount++; return x + 1;});

    assertEquals(1, execCount);

    i.value();

    assertEquals(2, execCount);
  }

  @Test
  @DisplayName("Creating a now Eval")
  public void testLaterEval() {

    Eval<Integer> i = Eval.later(() -> {execCount++; return 4;});

    i.value();
    i.value();
    i.value();

    assertEquals(1, execCount);

    i = i.map(x -> {execCount++; return x + 1;});

    assertEquals(1, execCount);

    i.value();

    assertEquals(2, execCount);
  }

  @Test
  @DisplayName("Creating an always Eval")
  public void testAlwaysEval() {

    Eval<Integer> i = Eval.always(() -> {execCount++; return 4;});

    i.value();
    i.value();
    i.value();

    assertEquals(3, execCount);

    i = i.map(x -> {execCount++; return x + 1;});

    assertEquals(3, execCount);

    i.value();

    assertEquals(5, execCount);

    i = i.memoize();

    assertEquals(5, i.value().intValue());
    assertEquals(5, i.value().intValue());
    assertEquals(5, i.value().intValue());

    assertEquals(7, execCount);
  }
}