package com.bootcamp;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Exercise: Lazy Evaluation Mechanics in Stream Intermediate Operations
 *
 * <p>Implement the methods below using: - Intermediate operations: filter, map, sorted, distinct,
 * peek, limit, skip - Terminal operations: collect, forEach, reduce, count, anyMatch, findFirst -
 * Lazy evaluation: understanding when intermediate ops execute - Infinite streams: Stream.iterate()
 * and Stream.generate() - Short-circuit operations: findFirst, findAny, limit
 */
public class StreamLazyEvaluation {

  // TODO 1: Return a list of the first 3 even numbers from the input list.
  //         Use filter() and limit() to demonstrate short-circuiting.
  //         Example: [1, 2, 3, 4, 5, 6, 7, 8] -> [2, 4, 6]
  public static List<Integer> firstThreeEven(List<Integer> numbers) {
    Stream<Integer> pipeline = numbers.stream().filter(n -> n % 2 == 0).limit(3);
    return pipeline.collect(toList());
  }

  // TODO 2: Return a list of squared values for numbers greater than the threshold.
  //         Use filter() and map() in sequence.
  //         Example: [1, 2, 3, 4, 5], threshold=2 -> [9, 16, 25]
  public static List<Integer> squaresAboveThreshold(List<Integer> numbers, int threshold) {
    Stream<Integer> pipeline = numbers.stream().filter(n -> n > threshold).map(n -> n * n);
    return pipeline.collect(toList());
  }

  // TODO 3: Return the sum of all elements, but ONLY if the list has at least one
  //         element greater than 100. Use anyMatch() to check, then reduce() to sum.
  //         If no element > 100 exists, return 0.
  //         Example: [10, 200, 5] -> 215 (has element > 100, so sum)
  //         Example: [10, 20, 5] -> 0 (no element > 100, return 0)
  public static int conditionalSum(List<Integer> numbers) {
    boolean hasGt100 = numbers.stream().anyMatch(n -> n > 100);
    if (!hasGt100) {
      return 0;
    }

    return numbers.stream().reduce(0, Integer::sum);
  }

  // TODO 4: Use peek() to log each element as it passes through the pipeline,
  //         then return a list of distinct, sorted strings that start with 'A'.
  //         The peek() should print each element before filtering.
  //         Example: ["Apple", "Banana", "Avocado", "Apple", "Cherry"] -> ["Avocado"]
  //         Note: "Apple" appears twice but should appear once in result (distinct)
  public static List<String> distinctSortedA(List<String> names) {
    Stream<String> pipeline =
        names.stream()
            .peek(System.out::println)
            .distinct()
            .filter(n -> n.startsWith("A"))
            .sorted(Comparator.naturalOrder());
    return pipeline.collect(toList());
  }

  // TODO 5: Create an infinite stream of Fibonacci numbers using Stream.iterate(),
  //         then return the first N numbers as a list.
  //         Use limit() to cap the infinite stream.
  //         Example: N=7 -> [0, 1, 1, 2, 3, 5, 8]
  public static List<Integer> fibonacci(int n) {
      return Stream.iterate(new int[]{0, 1}, pair -> new int[]{pair[1], pair[0] + pair[1]})
              .map(pair -> pair[0])
              .limit(n)
              .toList();
  }

  // TODO 6: Demonstrate lazy evaluation by counting how many times filter() is called.
  //         Return the count of elements processed by filter (not the result of filter).
  //         Use peek() before filter() to count invocations.
  //         Example: [1, 2, 3, 4, 5] -> 5 (filter called 5 times)
  public static long countFilterInvocations(List<Integer> numbers) {
      long[] count = {0};
      List<Integer> list = numbers.stream().peek(n -> count[0]++).filter(n -> n > 0).toList();
      return count[0];
  }

  // TODO 7: Use skip() and limit() to implement pagination.
  //         Return a sublist starting at offset `start` with length `pageSize`.
  //         Example: [0,1,2,3,4,5,6,7,8,9], start=3, pageSize=4 -> [3, 4, 5, 6]
  public static List<Integer> paginate(List<Integer> items, int start, int pageSize) {
    return items.stream().skip(start).limit(pageSize).toList();
  }

  // TODO 8: Create an infinite stream of random integers between 1 and 100 using
  //         Stream.generate(), filter out duplicates (distinct), and return the
  //         first 10 unique values as a list.
  public static List<Integer> tenUniqueRandoms() {
    return Stream.generate(() -> ThreadLocalRandom.current().nextInt(1, 101)).distinct().limit(10).toList();
  }

  public static void main(String[] args) {
    System.out.println("=== Lazy Evaluation Mechanics ===");
    System.out.println();

    List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    System.out.println("TODO 1 - First 3 Even:");
    System.out.println(firstThreeEven(nums));
    System.out.println();

    System.out.println("TODO 2 - Squares Above Threshold (3):");
    System.out.println(squaresAboveThreshold(nums, 3));
    System.out.println();

    System.out.println("TODO 3 - Conditional Sum:");
    System.out.println(conditionalSum(List.of(10, 200, 5)));
    System.out.println(conditionalSum(List.of(10, 20, 5)));
    System.out.println();

    List<String> names = List.of("Apple", "Banana", "Avocado", "Apple", "Cherry", "Apricot");
    System.out.println("TODO 4 - Distinct Sorted A:");
    System.out.println(distinctSortedA(names));
    System.out.println();

    System.out.println("TODO 5 - Fibonacci (7):");
    System.out.println(fibonacci(7));
    System.out.println();

    System.out.println("TODO 6 - Filter Invocations:");
    System.out.println(countFilterInvocations(nums));
    System.out.println();

    System.out.println("TODO 7 - Paginate (start=3, pageSize=4):");
    System.out.println(paginate(nums, 3, 4));
    System.out.println();

    System.out.println("TODO 8 - Ten Unique Randoms:");
    System.out.println(tenUniqueRandoms());
  }
}
