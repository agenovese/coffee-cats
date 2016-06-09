package ca.genovese.coffeecats.data;

import ca.genovese.coffeecats.data.list.List;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import java.util.NoSuchElementException;

import static org.junit.gen5.api.Assertions.*;

/**
 * Test for List, ListIterator, Cons, and Nil.
 */
public final class ListTest {
  /**
   * Test for Nil case.
   */
  @Test
  @DisplayName("Creating an empty List ")
  public void testListCreateEmptyList() {
    final List<Integer> is = List.of();

    //noinspection ThrowableResultOfMethodCallIgnored
    assertAll("address",
        () -> assertEquals(0, is.length(), "An emtpy List should have length 0"),
        () -> assertEquals("getHead on an empty list",
            expectThrows(NoSuchElementException.class, is::getHead).getMessage(),
            "Calling getHead on an empty list throws the NoSuchElementException"),
        () -> assertEquals("getTail on an empty list",
            expectThrows(NoSuchElementException.class, is::getTail).getMessage(),
            "Calling getTail on an empty list throws the NoSuchElementException"),
        () -> assertFalse(() -> is.iterator().hasNext(), "An empty list's iterator does not have a next item"),
        () -> assertThrows(NoSuchElementException.class, () -> is.iterator().next())
    );
  }

  /**
   * Test for Cons case.
   */
  @Test
  @DisplayName("Creating a non-empty List ")
  public void testListCreateNonEmptyList() {
    final List<Integer> is = List.of(1, 2, 3);

    assertAll("address",
        () -> assertEquals(3, is.length(),
            "length should return the length of the list"),
        () -> assertEquals(1, is.getHead().intValue(),
            "getHead should return the first item of the list"),
        () -> assertEquals(List.of(2, 3), is.getTail(),
            "getTail should return a list consisting of all but the first item of the list"),
        () -> assertTrue(() -> is.iterator().hasNext(),
            "A list's iterator.hasNext() should return true for a non-empty list"),
        () -> assertEquals(1, is.iterator().next().intValue(),
            "A list's iterator.next() should return the first item of a non-empty list")
    );
  }
}
