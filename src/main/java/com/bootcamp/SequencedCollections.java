package com.bootcamp;

import java.util.*;

public class SequencedCollections {

  public static void main(String[] args) {
    exercise1();
    exercise2();
    exercise3();
    exercise4();
  }

  // Exercise 1: SequencedCollection basics
  // TODO: Use the new SequencedCollection methods on a List.
  //
  // Java 21 introduced SequencedCollection — a unified interface for collections
  // that have a defined encounter order (first/last element).
  //
  // Create: ArrayList<String> with ["alpha", "beta", "gamma"]
  //
  // Methods to use:
  //   - getFirst()     → returns first element
  //   - getLast()      → returns last element
  //   - addFirst(e)    → inserts at beginning
  //   - addLast(e)     → inserts at end
  //   - removeFirst()  → removes and returns first
  //   - removeLast()   → removes and returns last
  //   - reversed()     → returns a reverse-ordered VIEW (not a copy)
  //
  // Key insight: reversed() returns a VIEW — changes to the view reflect in the original list.
  public static void exercise1() {
    System.out.println("=== Exercise 1: SequencedCollection basics ===");

    List<String> list = new ArrayList<>(List.of("alpha", "beta", "gamma"));

    // TODO: Print getFirst() and getLast()
    System.out.println("First: " + list.getFirst());
    System.out.println("Last: " + list.getLast());

    // TODO: Use addFirst("delta") and addLast("epsilon")
    list.addFirst("delta");
    list.addLast("epsilon");
    System.out.println("After adds: " + list);

    // TODO: Print the reversed view
    System.out.println("Reversed: " + list.reversed());

    // TODO: Remove first and last, print removed values
    System.out.println("Removed first: " + list.removeFirst());
    System.out.println("Removed last: " + list.removeLast());
    System.out.println("After removes: " + list);
  }

  // Exercise 2: SequencedSet with LinkedHashSet
  // TODO: Explore SequencedSet — a SequencedCollection with no duplicates.
  //
  // Create: LinkedHashSet<String> with ["one", "two", "three", "two", "four"]
  //         (note: "two" is duplicated — LinkedHashSet keeps insertion order, no dupes)
  //
  // LinkedHashSet implements SequencedSet, which extends SequencedCollection.
  // Same methods apply: getFirst(), getLast(), addFirst(), reversed(), etc.
  //
  // Key insight: SequencedSet adds reversed() returning a SequencedSet (not just Collection).
  // The reversed view is backed by the original set.
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: SequencedSet with LinkedHashSet ===");

    LinkedHashSet<String> set = new LinkedHashSet<>(List.of("one", "two", "three", "two", "four"));

    // TODO: Print first, last, and the full set
    System.out.println("Set: " + set);
    System.out.println("First: " + set.getFirst());
    System.out.println("Last: " + set.getLast());

    // TODO: Add "zero" at the beginning
    set.addFirst("zero");
    System.out.println("After addFirst: " + set);

    // TODO: Get reversed SequencedSet
    SequencedSet<String> reversed = set.reversed();
    System.out.println("Reversed type: " + reversed.getClass().getSimpleName());
    System.out.println("Reversed: " + reversed);

    // TODO: Verify that modifying the reversed view affects the original
    reversed.removeFirst();
    System.out.println("Original after removing from reversed: " + set);
  }

  // Exercise 3: SequencedMap with LinkedHashMap
  // TODO: Use SequencedMap methods for ordered key-value pairs.
  //
  // Create: LinkedHashMap<String, Integer> with:
  //   "apple" → 3, "banana" → 2, "cherry" → 5, "date" → 1
  //
  // SequencedMap methods:
  //   - firstEntry()      → Map.Entry<K,V> of the first entry
  //   - lastEntry()       → Map.Entry<K,V> of the last entry
  //   - pollFirstEntry()  → removes and returns first entry
  //   - pollLastEntry()   → removes and returns last entry
  //   - putFirst(k, v)    → inserts at the beginning
  //   - putLast(k, v)     → inserts at the end
  //   - reversed()        → reverse-ordered SequencedMap view
  //
  // Key insight: LinkedHashMap maintains insertion order by default.
  // SequencedMap gives you a uniform API for accessing both ends.
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: SequencedMap with LinkedHashMap ===");

    LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
    map.put("apple", 3);
    map.put("banana", 2);
    map.put("cherry", 5);
    map.put("date", 1);

    // TODO: Print first and last entries
    System.out.println("Map: " + map);
    System.out.println("First entry: " + map.firstEntry());
    System.out.println("Last entry: " + map.lastEntry());

    // TODO: Insert "elderberry" at the beginning and "fig" at the end
    map.putFirst("elderberry", 4);
    map.putLast("fig", 6);
    System.out.println("After inserts: " + map);

    // TODO: Poll (remove) first and last entries
    System.out.println("Polled first: " + map.pollFirstEntry());
    System.out.println("Polled last: " + map.pollLastEntry());
    System.out.println("After polls: " + map);
  }

  // Exercise 4: reversed() view semantics
  // TODO: Demonstrate that reversed() returns a live view, not a copy.
  //
  // Create: ArrayList<Integer> with [1, 2, 3, 4, 5]
  //
  // Steps:
  //   1. Get reversed view: List<Integer> rev = list.reversed()
  //   2. Print both list and rev
  //   3. Add 99 to the ORIGINAL list → rev should reflect it
  //   4. Remove from the REVERSED view → original should reflect it
  //   5. Clear the reversed view → original should be empty
  //
  // Key insight: reversed() is a cheap operation — it doesn't copy elements.
  // It creates a window into the same data structure.
  // This is useful for stack-like (LIFO) and queue-like (FIFO) operations
  // on the same collection without restructuring.
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: reversed() view semantics ===");

    ArrayList<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    List<Integer> reversed = list.reversed();

    System.out.println("Original: " + list);
    System.out.println("Reversed: " + reversed);

    // TODO: Add 99 to original, print reversed
    list.add(99);
    System.out.println("After adding 99 to original:");
    System.out.println("  Original: " + list);
    System.out.println("  Reversed: " + reversed);

    // TODO: Remove from reversed view (removeFirst = removes 99 which is last in original)
    reversed.removeFirst();
    System.out.println("After removing from reversed:");
    System.out.println("  Original: " + list);
    System.out.println("  Reversed: " + reversed);

    // TODO: Clear reversed, verify original is empty
    reversed.clear();
    System.out.println("After clearing reversed:");
    System.out.println("  Original: " + list);
    System.out.println("  Is empty: " + list.isEmpty());
  }
}
