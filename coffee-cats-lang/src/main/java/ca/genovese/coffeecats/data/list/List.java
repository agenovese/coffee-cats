package ca.genovese.coffeecats.data.list;

import ca.genovese.coffeecats.kind.Kind;

import java.util.Iterator;

/**
 * A class for immutable linked lists representing ordered collections of elements of type A.
 *
 * <p>This class comes with two implementing case classes Nil and Cons that implement the abstract
 * members isEmpty, head and tail.
 *
 * <p>This class is optimal for last-in-first-out (LIFO), stack-like access patterns.
 * If you need another access pattern, for example, random access or FIFO,
 * consider using a collection more suited to this than List.
 *
 * @param <A> The type of the items in the list
 */
public interface List<A> extends Iterable<A>, Kind<List, A> {
  /**
   * Utility method for creating a List.
   *
   * @param as The items to be added to the list
   * @param <A> The type of items in the list
   * @return A new list containing the specified items
   */
  @SafeVarargs
  static <A> List<A> of(final A... as) {
    List<A> list = new Nil<>();

    for (int i = as.length - 1; i >= 0; i--) {
      list = new Cons<>(as[i], list);
    }

    return list;
  }

  /**
   * Create a new List with an item added to the beginning of an existing List.
   * @param a The item to add to the list
   * @param as The list to use as a base
   * @param <A> The type of items in the List
   * @return The new List
   */
  static <A> List<A> cons(final A a, final List<A> as) {
    return new Cons<>(a, as);
  }

  /**
   * Calculate the length of the list.
   *
   * @return the length of the list
   */
  @SuppressWarnings("unused")
  default int length() {
    int length = 0;

    for (A a : this) {
      length++;
    }

    return length;
  }

  /**
   * Create a list which is the reverse of this list.
   * @return The reverse of this list
   */
  default List<A> reverse() {
    List<A> result = of();

    for (A a : this) {
      result = cons(a, result);
    }

    return result;
  }


  /**
   * Create a new List which is the concatenation of the provided list and this one.
   * @param as The list to concatenate to this list
   * @return The new, concatenated, List
   */
  default List<A> append(final List<A> as) {
    List<A> result = as;

    for (A a : reverse()) {
      result = cons(a, result);
    }

    return result;
  }

  /**
   * Returns an iterator over elements of this List.
   *
   * @return an Iterator.
   */
  @Override
  default Iterator<A> iterator() {
    return new ListIterator<>(this);
  }

  /**
   * Selects the first element of this List.
   * @return the first element of this List
   */
  A getHead();

  /**
   * Selects all elements except the first.
   * @return all elements except the first.
   */
  List<A> getTail();

  /**
   * Tests whether this List is empty.
   * @return true if this List is empty, false otherwise
   */
  boolean isEmpty();
}

