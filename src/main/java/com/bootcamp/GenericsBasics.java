package com.bootcamp;

import java.util.ArrayList;
import java.util.List;

public class GenericsBasics {

  public static void main(String[] args) {
    // exercise1();
    // exercise2();
    // exercise3();
    // exercise4();
    // exercise5();
    exercise6();
  }

  // Exercise 1: Generic classes
  // TODO: Implement a generic Box<T> that holds a single value.
  // A) Create a Box<String> with "Hello Generics" and print it
  // B) Create a Box<Integer> with 42 and print it
  // C) Create a Box<List<Integer>> with a list of numbers and print it
  // Observe how the same Box class works with completely different types.
  public static void exercise1() {
    System.out.println("=== Exercise 1: Generic classes ===");

    Box<String> stringBox = new Box<>("Hello Generics");
    Box<Integer> intBox = new Box<>(42);
    Box<List<Integer>> listBox = new Box<>(List.of(1, 2, 3));

    System.out.println("String box: " + stringBox);
    System.out.println("Int box: " + intBox);
    System.out.println("List box: " + listBox);
  }

  // Exercise 2: Bounded type parameters (extends)
  // TODO: Implement a generic method that finds the largest element.
  // Constraint: T must extend Comparable<T> so we can compare elements.
  //
  // Signature hint: public static <T extends Comparable<T>> T max(List<T> list)
  // Rules:
  //   - Return the largest element in the list
  //   - Throw IllegalArgumentException if the list is null or empty
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Bounded type parameters ===");

    List<Integer> numbers = List.of(3, 7, 2, 9, 5);
    List<String> words = List.of("apple", "zebra", "banana");

    System.out.println("Max number: " + max(numbers));
    System.out.println("Max word: " + max(words));
  }

  // Exercise 3: Upper-bounded wildcards (? extends T) — read-only / covariant
  // TODO: Implement a method that sums all Integer values in a List<? extends Number>.
  // This accepts List<Integer>, List<Double>, List<Number>, etc.
  //
  // Signature hint: public static double sumOfNumbers(List<? extends Number> numbers)
  // Rules:
  //   - Iterate through the list and sum each Number's doubleValue()
  //   - Return 0.0 for an empty list
  //   - You CANNOT add elements to a List<? extends Number> — only read from it
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Upper-bounded wildcards ===");

    List<Integer> ints = List.of(1, 2, 3);
    List<Double> doubles = List.of(1.5, 2.5, 3.5);

    System.out.println("Sum of ints: " + sumOfNumbers(ints));
    System.out.println("Sum of doubles: " + sumOfNumbers(doubles));
  }

  // Exercise 4: Lower-bounded wildcards (? super T) — write-only / contravariant
  // TODO: Implement a method that adds integers 1 through n to a collection.
  // The collection must accept Integers, so use List<? super Integer>.
  //
  // Signature hint: public static void fillWithIntegers(List<? super Integer> list, int n)
  // Rules:
  //   - Add integers from 1 to n (inclusive) to the provided list
  //   - You CAN write to a List<? super Integer> — that's the point of lower bounds
  //   - You CANNOT read specific Integer values from List<? super Integer>
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Lower-bounded wildcards ===");

    List<Number> numberList = new ArrayList<>();
    fillWithIntegers(numberList, 5);
    System.out.println("Filled list: " + numberList);
    // Expected: [1, 2, 3, 4, 5]
  }

  // Exercise 5: Generic methods
  // TODO: Implement two generic methods:
  //
  // A) First element with safety:
  //    Signature hint: public static <T> T firstOrDefault(List<T> list, T defaultValue)
  //    Rules:
  //      - Return the first element if the list is non-empty
  //      - Return defaultValue if the list is null or empty
  //      - The type of defaultValue must match T
  //
  // B) Pair builder:
  //    Signature hint: public static <A, B> String pairToString(A first, B second)
  //    Rules:
  //      - Return a string in the format "(first, second)"
  //      - This method has TWO type parameters, demonstrating multi-type generics
  public static void exercise5() {
    System.out.println("\n=== Exercise 5: Generic methods ===");

    List<String> names = List.of("Alice", "Bob");
    List<String> empty = List.of();

    System.out.println("First name: " + firstOrDefault(names, "unknown"));
    System.out.println("First empty: " + firstOrDefault(empty, "unknown"));

    System.out.println("Pair: " + pairToString("Java", 25));
    System.out.println("Pair: " + pairToString(42, 3.14));
  }

  // Exercise 6: Type inference and diamond operator
  // TODO: Explore how Java infers generic types.
  // A) Create an ArrayList of Strings using the diamond operator: new ArrayList<>()
  // B) Create a List.of() — observe how Java infers the type from context
  // C) Mix bounded wildcards and inference:
  //    Write a static method that copies elements from List<? extends T> to List<T>
  //
  // Signature hint: public static <T> void safeCopy(List<? extends T> source, List<T> dest)
  // Rules:
  //   - Copy all elements from source into dest
  //   - This demonstrates why ? extends T is safe for reading but requires a generic method for
  // writing
  public static void exercise6() {
    System.out.println("\n=== Exercise 6: Type inference and diamond operator ===");

    List<String> inferred = new ArrayList<>(); // diamond operator infers String
    inferred.add("first");
    inferred.add("second");

    List<Integer> source = List.of(10, 20, 30);
    List<Integer> dest = new ArrayList<>();
    safeCopy(source, dest);
    System.out.println("Copied: " + dest);
  }

  // =============================================
  // TODO: Create your generic class and methods below
  // =============================================

  // A) For Exercise 1: Generic classes
  static class Box<T> {
    private T value;

    Box(T value) {
      this.value = value;
    }
    ;

    @Override
    public String toString() {
      return value.toString();
    }
  }

  // B) Bounded generic method max() for Exercise 2
  public static <T extends Comparable<T>> T max(List<T> list) {
    if (list == null || list.isEmpty()) {
      throw new IllegalArgumentException();
    }

    T max = list.getFirst();

    for (int i = 1; i < list.size(); i++) {
      if (list.get(i).compareTo(max) > 0) {
        max = list.get(i);
      }
    }
    return max;
  }

  // C) Upper-bounded wildcard method sumOfNumbers() for Exercise 3
  public static double sumOfNumbers(List<? extends Number> numbers) {
    // Rules:
    //   - Iterate through the list and sum each Number's doubleValue()
    //   - Return 0.0 for an empty list
    //   - You CANNOT add elements to a List<? extends Number> — only read from it
    if (numbers == null || numbers.isEmpty()) {
      return 0.0;
    }
    return numbers.stream()
        .reduce(0.0, (subTotal, number) -> subTotal + number.doubleValue(), Double::sum);
  }

  // D) Lower-bounded wildcard method fillWithIntegers() for Exercise 4
  public static void fillWithIntegers(List<? super Integer> list, int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n must be non-negative");
    }
    for (int i = 1; i <= n; i++) {
      list.add(i);
    }
  }

  // E) Generic methods firstOrDefault() and pairToString() for Exercise 5
  public static <T> T firstOrDefault(List<T> list, T defaultValue) {
    // A) First element with safety:
    //    Signature hint: public static <T> T firstOrDefault(List<T> list, T defaultValue)
    //    Rules:
    //      - Return the first element if the list is non-empty
    //      - Return defaultValue if the list is null or empty
    //      - The type of defaultValue must match T
    return list == null || list.isEmpty() ? defaultValue : list.getFirst();
  }

  public static <A, B> String pairToString(A first, B second) {
    //
    // B) Pair builder:
    //    Signature hint: public static <A, B> String pairToString(A first, B second)
    //    Rules:
    //      - Return a string in the format "(first, second)"
    //      - This method has TWO type parameters, demonstrating multi-type generics
    return "(" + first + ", " + second + ")";
  }

  // F) Type-safe copy method safeCopy() for Exercise 6
  public static <T> void safeCopy(List<? extends T> source, List<T> dest) {
    // Rules:
    //   - Copy all elements from source into dest
    //   - This demonstrates why ? extends T is safe for reading but requires a generic method for
    // writing
    dest.addAll(source);
  }
}
