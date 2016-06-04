package ca.genovese.coffeecats.data;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import java.util.NoSuchElementException;

import static org.junit.gen5.api.Assertions.*;

public class OptionTest {
  @Test
  @DisplayName("Creating a None")
  public void testOptionCreateNone() {
    final Option<Integer> i = Option.of(null);

    //noinspection ThrowableResultOfMethodCallIgnored
    assertAll(
        () -> assertFalse(i.isDefined(), "isDefined should return false for None"),
        () -> assertEquals(1, i.getOrElse(1).intValue(),
            "Calling getOrElse on a none value returns the provided default"),
        () -> assertEquals("None()", i.toString(), "None.toString returns None()"),
        () -> assertEquals("get() called on a None",
            expectThrows(NoSuchElementException.class, i::get).getMessage(),
            "Calling get on None throws the NoSuchElementException"),
        () -> assertFalse(() -> i.iterator().hasNext(),
            "hasNext called on None.iterator should return false"),
        () -> expectThrows(NoSuchElementException.class, () -> i.iterator().next())
    );
  }

  @Test
  @DisplayName("Creating a Some")
  public void testOptionCreateSome() {
    final Option<Integer> i = Option.of(1);

    assertAll(
        () -> assertTrue(i.isDefined(), "isDefined should return true for Some"),
        () -> assertEquals(1, i.getOrElse(10).intValue(),
            "Calling getOrElse on a Some value returns the value from the Some"),
        () -> assertEquals(1, i.get().intValue(),
            "Calling get on a Some value returns the value from the Some"),
        () -> assertEquals("Some(value=1)", i.toString(),
            "Calling toString on a Some value"),
        () -> assertTrue(i.iterator().hasNext(),
            "Calling iterator.hasNext on a Some value should return true"),
        () -> assertEquals(1, i.iterator().next().intValue(),
            "Calling iterator.next on a Some value should return the Some value")
    );
  }
}