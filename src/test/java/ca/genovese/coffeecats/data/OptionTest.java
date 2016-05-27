package ca.genovese.coffeecats.data;

import static org.junit.gen5.api.Assertions.*;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import java.util.NoSuchElementException;

public class OptionTest {
  @Test
  @DisplayName("Creating a None")
  public void testOptionCreateNone() {
    Option<Integer> i = Option.of(null);

    //noinspection ThrowableResultOfMethodCallIgnored
    assertAll(
        () -> assertFalse(i.isDefined()),
        () -> expectThrows(NoSuchElementException.class, i::get),
        () -> assertEquals(1, i.getOrElse(1).intValue()),
        () -> assertEquals("None()", i.toString()),
        () -> assertEquals("get() called on a None",
            expectThrows(NoSuchElementException.class, i::get).getMessage()),
        () -> assertFalse(() -> i.iterator().hasNext()),
        () -> expectThrows(NoSuchElementException.class, () -> i.iterator().next())
    );
  }

  @Test
  @DisplayName("Creating a Some")
  public void testOptionCreateSome() {
    Option<Integer> i = Option.of(1);

    //noinspection ThrowableResultOfMethodCallIgnored
    assertAll(
        () -> assertTrue(i.isDefined()),
        () -> assertEquals(1, i.getOrElse(10).intValue()),
        () -> assertEquals(1, i.get().intValue()),
        () -> assertEquals("Some(a=1)", i.toString()),
        () -> assertTrue(i.iterator().hasNext()),
        () -> assertEquals(1, i.iterator().next().intValue())
    );
  }
}