package com.bootcamp;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MethodReferences {

  public static void main(String[] args) {
    // exercise1();
    // exercise2();
    // exercise3();
    // exercise4();
    exercise5();
  }

  // Exercise 1: Static method references
  // TODO: Implement each task using a static method reference:
  // A) Use Integer::parseInt to convert "42" to an int
  // B) Use MethodReferences::isEven to filter even numbers from a list
  // C) Use Math::max with reduce to find the largest number
  // Print all results
  public static void exercise1() {
    System.out.println("=== Exercise 1: Static method references ===");

    // A) Integer::parseInt — static method on Integer class
    Function<String, Integer> parser = Integer::parseInt;
    int parsed = parser.apply("42");
    System.out.println("Parsed: " + parsed);

    // B) MethodReferences::isEven — static method on THIS class
    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> evens =
        numbers.stream().filter(MethodReferences::isEven).toList(); // TODO: filter using
    // MethodReferences::isEven
    System.out.println("Evens: " + evens);

    // C) Math::max — static method on Math class, used as a BinaryOperator
    int max = 0; // TODO: reduce the list using Math::max
    max = numbers.stream().reduce(0, Math::max);
    System.out.println("Max: " + max);
  }

  // Helper method for Exercise 1
  public static boolean isEven(int n) {
    return n % 2 == 0;
  }

  // Exercise 2: Instance method references (particular object)
  // TODO: Implement using an instance method reference bound to a specific
  // object:
  // A) Create a String instance: String prefix = "[LOG] "
  // B) Use prefix::concat with map to prepend it to each word
  // Hint: String.concat(String) is an instance method
  // Print the result
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Instance method references (particular object) ===");

    List<String> words = List.of("hello", "world", "java");

    // TODO: create a String instance for the prefix
    String prefix = "[LOG] ";

    // TODO: use prefix::concat with map to prepend prefix to each word
    List<String> result = words.stream().map(prefix::concat).toList();

    System.out.println("Result: " + result);
  }

  // Exercise 3: Instance method references (arbitrary object)
  // TODO: Implement using a method reference that works on ANY object of a given
  // type:
  // A) Use String::toLowerCase with map to lowercase all strings
  // B) Use String::length with map to get the length of each string
  // C) Use String::isEmpty with filter to keep only non-empty strings
  // Print each result
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Instance method references (arbitrary object) ===");

    List<String> words = List.of("HELLO", "World", "JAVA", "", "  ");

    // A) String::toLowerCase — applied to each element
    List<String> lowercased =
        words.stream().map(String::toLowerCase).toList(); // TODO: use String::toLowerCase
    // with map
    System.out.println("Lowercased: " + lowercased);

    // B) String::length — applied to each element
    List<Integer> lengths =
        words.stream().map(String::length).toList(); // TODO: use String::length with map
    System.out.println("Lengths: " + lengths);

    // C) String::isEmpty — applied to each element
    List<String> nonEmpty =
        words.stream().filter(Predicate.not(String::isEmpty)).toList(); // TODO: use
    // String::isEmpty with
    // filter (negate it!)
    System.out.println("Non-empty: " + nonEmpty);
  }

  // Exercise 4: Constructor references
  // TODO: Implement using constructor references to create new objects:
  // A) Use StringBuilder::new with map to create a List<StringBuilder>
  // B) Use String::new with map to wrap each int in a String object
  // Print each result
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Constructor references ===");

    List<Integer> numbers = List.of(1, 2, 3, 4, 5);

    // A) StringBuilder::new — creates a new StringBuilder for each element
    List<StringBuilder> builders = numbers.stream().map(StringBuilder::new).toList(); // TODO: use
    // StringBuilder::new with map
    System.out.println("Builders: " + builders);

    // B) String::new — creates a new String from each int
    List<String> strings =
        numbers.stream().map(String::valueOf).toList(); // TODO: use String::new with map
    System.out.println("Strings: " + strings);
  }

  // Exercise 5: Combining method references in a pipeline
  // TODO: Build a complete processing pipeline using ONLY method references (no
  // lambdas):
  // Given a list of names, filter those starting with "J", convert to uppercase,
  // sort them, and collect to a comma-separated string.
  // Use: String::startsWith, String::toUpperCase, String::compareTo,
  // Collectors.joining
  public static void exercise5() {
    System.out.println("\n=== Exercise 5: Method reference pipeline ===");

    List<String> names = List.of("James", "Anna", "John", "Maria", "Jake", "Sofia");

    // TODO: build a pipeline using only method references:
    // 1. filter: name -> name.startsWith("J") → use String::startsWith
    // 2. map: name -> name.toUpperCase() → use String::toUpperCase
    // 3. sorted: (a, b) -> a.compareTo(b) → use String::compareTo
    // 4. collect: Collectors.joining(", ")
    String result =
        names.stream()
            .filter(MethodReferences::startsWithLetterJ)
            .map(String::toUpperCase)
            .sorted(String::compareTo)
            .collect(Collectors.joining(", "));

    System.out.println("Result: " + result);
  }

  // Helper method for Exercise 5
  public static boolean startsWithLetterJ(String word) {
    return word.startsWith("J");
  }
}
