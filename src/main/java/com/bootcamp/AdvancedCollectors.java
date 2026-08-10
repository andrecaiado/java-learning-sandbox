package com.bootcamp;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Exercise: Advanced Reductions, Custom Collectors, and Grouping Operations
 *
 * <p>Implement the methods below using: - Collectors.groupingBy() with downstream collectors -
 * Collectors.partitioningBy() for boolean splits - Collectors.toMap() with merge functions - Custom
 * collector implementation via Collector.of() - Advanced reduce() patterns (identity and
 * no-identity) - Collectors.collectingAndThen() for post-processing - Collectors.joining() with
 * delimiter, prefix, suffix
 */
public class AdvancedCollectors {

  // TODO 1: Group words by their length using groupingBy().
  //         Return a Map<Integer, List<String>> where key = word length.
  //         Example: ["Hi", "Hello", "Hey", "Go"] -> {2=[Hi, Go], 3=[Hey], 5=[Hello]}
  public static Map<Integer, List<String>> groupByLength(List<String> words) {
    return words.stream().collect(Collectors.groupingBy(String::length));
  }

  // TODO 2: Group numbers by even/odd, then for each group collect the sum.
  //         Use groupingBy() with a downstream summingInt collector.
  //         Return a Map<String, Integer> where key is "even" or "odd".
  //         Example: [1, 2, 3, 4, 5, 6] -> {even=12, odd=9}
  public static Map<String, Integer> sumEvenVsOdd(List<Integer> numbers) {
    return numbers.stream()
        .collect(
            Collectors.groupingBy(n -> n % 2 == 0 ? "even" : "odd", Collectors.summingInt(n -> n)));
  }

  // TODO 3: Partition a list of integers into two groups: positives (>= 0) and negatives (< 0).
  //         Use Collectors.partitioningBy() with a predicate.
  //         Return a Map<Boolean, List<Integer>> where true = non-negative, false = negative.
  //         Example: [3, -1, 0, -5, 7] -> {true=[3, 0, 7], false=[-1, -5]}
  public static Map<Boolean, List<Integer>> partitionPositivesNegatives(List<Integer> numbers) {
    return numbers.stream().collect(Collectors.partitioningBy(n -> n >= 0));
  }

  // TODO 4: Build a Map<String, Integer> from a list of strings where key = string, value = length.
  //         Use Collectors.toMap() with identity key mapper and String::length value mapper.
  //         Example: ["cat", "dog", "bird"] -> {cat=3, dog=3, bird=4}
  public static Map<String, Integer> stringToLength(List<String> words) {
    return words.stream().collect(Collectors.toMap(n -> n, String::length));
  }

  // TODO 5: Build a Map<Character, Long> counting occurrences of each first character.
  //         Use groupingBy() with Collectors.counting() as downstream.
  //         Example: ["Apple", "Avocado", "Banana", "Blueberry"] -> {A=2, B=2}
  public static Map<Character, Long> countByFirstChar(List<String> words) {
    return words.stream().collect(Collectors.groupingBy(w -> w.charAt(0), Collectors.counting()));
  }

  // TODO 6: Group strings by their first character, but collect only the UPPERCASE versions
  //         using groupingBy() with mapping() downstream collector.
  //         Return Map<Character, Set<String>> where values are uppercase strings.
  //         Example: ["Apple", "avocado", "Banana"] -> {A=[APPLE], B=[BANANA]}
  public static Map<Character, Set<String>> groupByFirstCharUpperCase(List<String> words) {
    return words.stream()
        .collect(
            Collectors.groupingBy(
                w -> w.charAt(0), Collectors.mapping(String::toUpperCase, Collectors.toSet())));
  }

  // TODO 7: Implement a custom collector that joins strings with a separator,
  //         but only includes strings longer than `minLength`.
  //         Use Collector.of() with supplier, accumulator, combiner, and finisher.
  //         Example: ["Hi", "Hello", "Hey", "Go"], separator=", ", minLength=3 -> "Hello, Hey"
  public static String joinLongerThan(List<String> words, String separator, int minLength) {
    return words.stream()
        .collect(
            Collector.of(
                StringBuilder::new,
                (sb, s) -> {
                  if (s.length() > minLength) {
                    if (!sb.isEmpty()) sb.append(separator);
                    sb.append(s);
                  }
                },
                (sb1, sb2) -> {
                  if (!sb2.isEmpty()) {
                    if (!sb1.isEmpty()) sb1.append(separator);
                    sb1.append(sb2);
                  }
                  return sb1;
                },
                StringBuilder::toString));
  }

  // TODO 8: Use reduce() to find the maximum length among all strings in the list.
  //         Use the 2-argument reduce(identity, accumulator) form.
  //         Return 0 for empty list.
  //         Example: ["Cat", "Elephant", "Dog"] -> 8
  public static int maxLength(List<String> words) {
    return words.stream().map(String::length).reduce(0, Math::max);
  }

  // TODO 9: Use reduce() with no identity to concatenate all strings with a hyphen.
  //         Use the 1-argument reduce(BinaryOperator) form and return empty string if list is
  // empty.
  //         Example: ["Java", "Stream", "API"] -> "Java-Stream-API"
  public static String concatWithHyphen(List<String> words) {
    return words.stream().reduce((a, b) -> a + "-" + b).orElse("");
  }

  // TODO 10: Use collectingAndThen() to collect into an unmodifiable list,
  //          then return its size.
  //          This demonstrates post-processing after collection.
  //          Example: ["a", "b", "c"] -> 3
  public static int countAndReturnSize(List<String> words) {
    return words.stream().collect(Collectors.collectingAndThen(toList(), List::size));
  }

  public static void main(String[] args) {
    System.out.println("=== Advanced Reductions, Custom Collectors, and Grouping Operations ===");
    System.out.println();

    List<String> words = List.of("Hi", "Hello", "Hey", "Go", "Java", "Stream");
    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, -1, -3, 0);

    System.out.println("TODO 1 - Group By Length:");
    System.out.println(groupByLength(words));
    System.out.println();

    System.out.println("TODO 2 - Sum Even vs Odd:");
    System.out.println(sumEvenVsOdd(numbers));
    System.out.println();

    System.out.println("TODO 3 - Partition Positives/Negatives:");
    System.out.println(partitionPositivesNegatives(numbers));
    System.out.println();

    System.out.println("TODO 4 - String to Length Map:");
    System.out.println(stringToLength(words));
    System.out.println();

    List<String> fruits = List.of("Apple", "Avocado", "Banana", "Blueberry", "Cherry");
    System.out.println("TODO 5 - Count By First Char:");
    System.out.println(countByFirstChar(fruits));
    System.out.println();

    System.out.println("TODO 6 - Group By First Char (Upper):");
    System.out.println(groupByFirstCharUpperCase(fruits));
    System.out.println();

    System.out.println("TODO 7 - Join Longer Than 3:");
    System.out.println(joinLongerThan(words, ", ", 3));
    System.out.println();

    System.out.println("TODO 8 - Max Length:");
    System.out.println(maxLength(words));
    System.out.println();

    System.out.println("TODO 9 - Concat With Hyphen:");
    System.out.println(concatWithHyphen(words));
    System.out.println();

    System.out.println("TODO 10 - Count (Collecting And Then):");
    System.out.println(countAndReturnSize(words));
  }
}
