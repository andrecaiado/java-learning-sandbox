package com.bootcamp;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * Exercise: Parallel Streams Execution Models and Thread-Safety Pitfalls
 *
 * <p>Implement the methods below using: - parallelStream() and stream().parallel() - Fork/Join
 * framework concepts - Thread-safety pitfalls and race conditions - Concurrent collectors
 * (Collectors.toConcurrentMap, groupingByConcurrent) - Order preservation vs non-deterministic
 * ordering - Side-effect avoidance patterns - Custom thread pools with parallel streams - Common
 * pool configuration - When NOT to use parallel streams
 */
public class ParallelStreams {

  // TODO 1: Convert a sequential stream to parallel and return the sum of squares.
  //         Use parallel() on the stream and map each element to its square.
  //         Compare the result with sequential execution to verify correctness.
  //         Example: [1, 2, 3, 4, 5] -> 55 (1+4+9+16+25)
  public static int sumOfSquaresParallel(List<Integer> numbers) {
    throw new UnsupportedOperationException("Implement TODO 1");
  }

  // TODO 2: Demonstrate thread-safety issues with parallel streams.
  //         The following code has a race condition. Fix it using proper synchronization.
  //         Return the count of elements processed by each thread.
  //         Hint: Consider using concurrent data structures or synchronization.
  public static Map<String, AtomicInteger> countByThreadParallel(List<String> items) {
    Map<String, AtomicInteger> threadCounts = new HashMap<>();
    items.parallelStream()
        .forEach(
            item -> {
              String threadName = Thread.currentThread().getName();
              threadCounts
                  .computeIfAbsent(threadName, k -> new AtomicInteger(0))
                  .incrementAndGet();
            });
    return threadCounts;
  }

  // TODO 3: Use Collectors.toConcurrentMap to build a map in parallel.
  //         Convert a list of strings to a map where key=uppercase, value=length.
  //         Ensure the result is a ConcurrentHashMap (thread-safe).
  //         Example: ["hello", "world"] -> {"HELLO": 5, "WORLD": 5}
  public static Map<String, Integer> toConcurrentMapParallel(List<String> words) {
    throw new UnsupportedOperationException("Implement TODO 3");
  }

  // TODO 4: Compare parallel vs sequential performance.
  //         Run a CPU-intensive operation (e.g., sum of prime checks) on both.
  //         Return a map with keys "sequential_ms" and "parallel_ms" containing elapsed times.
  //         Use System.nanoTime() for measurement.
  public static Map<String, Long> compareParallelPerformance(int limit) {
    throw new UnsupportedOperationException("Implement TODO 4");
  }

  // TODO 5: Demonstrate order preservation issues with parallel streams.
  //         Process a list of integers in parallel and collect to a List.
  //         Check if the output order matches the input order.
  //         Return true if order is preserved, false otherwise.
  //         Hint: parallel streams don't guarantee order unless using ordered operations.
  public static boolean checkOrderPreservation(List<Integer> numbers) {
    throw new UnsupportedOperationException("Implement TODO 5");
  }

  // TODO 6: Use parallel streams with a custom ForkJoinPool.
  //         Process items using a thread pool with 4 threads.
  //         Return the list of thread names that processed the items.
  //         Hint: Use ForkJoinPool.commonPool() or create a custom one.
  public static List<String> parallelWithCustomPool(List<String> items) {
    throw new UnsupportedOperationException("Implement TODO 6");
  }

  // TODO 7: Implement a parallel reduce operation safely.
  //         Use reduce() with parallel streams to sum a list of integers.
  //         Ensure thread-safety by using the correct overload of reduce.
  //         Return the total sum.
  public static int safeParallelReduce(List<Integer> numbers) {
    throw new UnsupportedOperationException("Implement TODO 7");
  }

  // TODO 8: Demonstrate when NOT to use parallel streams.
  //         Process a small list (10 elements) in parallel.
  //         Return true if parallel was slower than sequential, false otherwise.
  //         Hint: Overhead of thread creation can outweigh benefits for small datasets.
  public static boolean parallelOverheadDemo(int smallListSize) {
    throw new UnsupportedOperationException("Implement TODO 8");
  }

  // TODO 9: Use groupingByConcurrent for parallel grouping.
  //         Group a list of words by their first letter in parallel.
  //         Return a ConcurrentMap<Character, List<String>>.
  //         Example: ["Apple", "Avocado", "Banana"] -> {'A': ["Apple", "Avocado"], 'B': ["Banana"]}
  public static ConcurrentMap<Character, List<String>> groupingByConcurrentParallel(
      List<String> words) {
    throw new UnsupportedOperationException("Implement TODO 9");
  }

  // TODO 10: Implement a parallel sort with a custom comparator.
  //          Sort a large list of integers in parallel using parallelStream().sorted().
  //          Return the sorted list.
  //          Verify the result is correctly sorted.
  public static List<Integer> parallelSort(List<Integer> numbers) {
    throw new UnsupportedOperationException("Implement TODO 10");
  }

  public static void main(String[] args) {
    System.out.println("=== Parallel Streams Execution Models and Thread-Safety Pitfalls ===");
    System.out.println();

    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println("TODO 1 - Sum of Squares Parallel:");
    System.out.println(sumOfSquaresParallel(numbers));
    System.out.println();

    List<String> items = List.of("a", "b", "c", "d", "e", "f", "g", "h");
    System.out.println("TODO 2 - Count By Thread Parallel:");
    Map<String, AtomicInteger> counts = countByThreadParallel(items);
    counts.forEach((k, v) -> System.out.println(k + ": " + v.get()));
    System.out.println();

    List<String> words = List.of("hello", "world", "java", "streams");
    System.out.println("TODO 3 - To Concurrent Map Parallel:");
    System.out.println(toConcurrentMapParallel(words));
    System.out.println();

    System.out.println("TODO 4 - Compare Parallel Performance:");
    Map<String, Long> perf = compareParallelPerformance(100000);
    perf.forEach((k, v) -> System.out.println(k + ": " + v + "ms"));
    System.out.println();

    System.out.println("TODO 5 - Check Order Preservation:");
    System.out.println(checkOrderPreservation(numbers));
    System.out.println();

    System.out.println("TODO 6 - Parallel With Custom Pool:");
    System.out.println(parallelWithCustomPool(items));
    System.out.println();

    System.out.println("TODO 7 - Safe Parallel Reduce:");
    System.out.println(safeParallelReduce(numbers));
    System.out.println();

    System.out.println("TODO 8 - Parallel Overhead Demo:");
    System.out.println(parallelOverheadDemo(10));
    System.out.println();

    System.out.println("TODO 9 - Grouping By Concurrent Parallel:");
    System.out.println(groupingByConcurrentParallel(words));
    System.out.println();

    System.out.println("TODO 10 - Parallel Sort:");
    System.out.println(parallelSort(numbers));
  }
}