package ca.genovese.coffeecats.data;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import java.util.NoSuchElementException;

import static org.junit.gen5.api.Assertions.*;

public class ListTest {
  @Test
  @DisplayName("Creating an empty List ")
  public void testListCreateEmptyList() {
    List<Integer> is = List.of();

    //noinspection ThrowableResultOfMethodCallIgnored
    assertAll("address",
        () -> assertEquals(0, is.length()),
        () -> expectThrows(NoSuchElementException.class, is::getHead),
        () -> assertEquals("getHead on an empty list",
            expectThrows(NoSuchElementException.class, is::getHead).getMessage()),
        () -> expectThrows(NoSuchElementException.class, is::getTail),
        () -> assertEquals("getTail on an empty list",
            expectThrows(NoSuchElementException.class, is::getTail).getMessage()),
        () -> assertFalse(() -> is.iterator().hasNext()),
        () -> expectThrows(NoSuchElementException.class, () -> is.iterator().next())
    );
  }

  @Test
  @DisplayName("Creating a non-empty List ")
  public void testListCreateNonEmptyList() {
    List<Integer> is = List.of(1, 2, 3);

    //noinspection ThrowableResultOfMethodCallIgnored
    assertAll("address",
        () -> assertEquals(3, is.length()),
        () -> assertEquals(1, is.getHead().intValue()),
        () -> assertEquals(List.of(2, 3), is.getTail()),
        () -> assertTrue(() -> is.iterator().hasNext()),
        () -> assertEquals(1, is.iterator().next().intValue())
    );
  }
}