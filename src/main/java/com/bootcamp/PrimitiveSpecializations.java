package com.bootcamp;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveSpecializations {

    public static void main(String[] args) {
        // exercise1();
        // exercise2();
        // exercise3();
        // exercise4();
        exercise5();
    }

    // Exercise 1: Auto-boxing cost demo
    // TODO: Sum numbers 1 to 1_000_000 using both approaches:
    // A) Stream<Integer> with mapToInt + reduce
    // B) IntStream.rangeClosed().sum()
    // Print both results and confirm they match
    // Use System.nanoTime() to measure each approach
    public static void exercise1() {
        long startBoxed = System.nanoTime();
        // TODO: implement boxed version using Stream.iterate(), mapToInt(), sum()
        int sumBoxed = Stream.iterate(1, x -> x + 1).limit(1_000_000).mapToInt(Integer::intValue).sum();
        long elapsedBoxed = System.nanoTime() - startBoxed;

        long startPrimitive = System.nanoTime();
        // TODO: implement primitive version using IntStream.rangeClosed().sum()
        int sumPrimitive = IntStream.rangeClosed(1, 1_000_000).sum();
        long elapsedPrimitive = System.nanoTime() - startPrimitive;

        System.out.println("=== Exercise 1: Auto-boxing cost ===");
        System.out.println("Boxed sum:      " + sumBoxed + " (" + elapsedBoxed / 1_000_000 + " ms)");
        System.out.println("Primitive sum:  " + sumPrimitive + " (" + elapsedPrimitive / 1_000_000 + " ms)");
        System.out.println("Results match:  " + (sumBoxed == sumPrimitive));
    }

    // Exercise 2: IntConsumer vs Consumer<Integer>
    // TODO: Given a list of integers [10, 20, 30, 40, 50]:
    // A) Use forEach() with Consumer<Integer> that prints each number
    // B) Use toIntStream().forEach() with IntConsumer that prints each number
    // Notice the type difference in the lambda parameter
    public static void exercise2() {
        List<Integer> numbers = List.of(10, 20, 30, 40, 50);

        System.out.println("\n=== Exercise 2: IntConsumer vs Consumer<Integer> ===");

        // TODO: create a Consumer<Integer> that prints each number
        Consumer<Integer> boxedConsumer = x -> System.out.println(x);
        System.out.print("Using Consumer<Integer>: ");
        // TODO: use numbers.forEach() with your Consumer
        numbers.forEach(boxedConsumer);

        // TODO: create an IntConsumer that prints each number
        IntConsumer primitiveConsumer = x -> System.out.println(x);
        System.out.print("Using IntConsumer:      ");
        // TODO: use numbers.stream().mapToInt().forEach() with your IntConsumer
        numbers.stream().mapToInt(Integer::intValue).forEach(primitiveConsumer);
    }

    // Exercise 3: IntFunction<R> — avoid boxing on input
    // TODO: Given a list of numeric strings ["10", "20", "30", "40", "50"]:
    // A) Use Stream.map() with Function<String, Integer> to parse and double each
    // B) Use mapToInt().mapToObj() with IntFunction<Integer> to parse and double
    // each
    // Compare the signatures: Function takes String, IntFunction takes int
    public static void exercise3() {
        List<String> numericStrings = List.of("10", "20", "30", "40", "50");

        System.out.println("\n=== Exercise 3: IntFunction<R> ===");

        // TODO: create a Function<String, Integer> that parses and doubles
        Function<String, Integer> boxedFunction = x -> Integer.parseInt(x) * 2;
        List<Integer> boxedResult = null;
        // TODO: use numericStrings.stream().map(boxedFunction).toList()
        boxedResult = numericStrings.stream().map(boxedFunction).toList();
        System.out.println("Function<String, Integer>: " + boxedResult);

        // TODO: create an IntFunction<Integer> that doubles an int
        IntFunction<Integer> primitiveFunction = x -> x * 2;
        List<Integer> primitiveResult = null;
        // TODO: use
        // numericStrings.stream().mapToInt(Integer::parseInt).mapToObj(primitiveFunction).toList()
        primitiveResult = numericStrings.stream().mapToInt(Integer::parseInt).mapToObj(primitiveFunction).toList();
        System.out.println("IntFunction<Integer>:      " + primitiveResult);
    }

    // Exercise 4: IntSupplier & IntPredicate
    // TODO: Create:
    // A) An IntSupplier that returns a random int between 0 and 99 (inclusive)
    // B) An IntPredicate that returns true if the number is divisible by 3
    // Then use them with IntStream.generate() and .filter() to get 10 random
    // multiples of 3. Print the results.
    public static void exercise4() {
        System.out.println("\n=== Exercise 4: IntSupplier & IntPredicate ===");

        // TODO: create an IntSupplier using Random.nextInt(100)
        IntSupplier randomSupplier = () -> new Random().nextInt(100);
        ;

        // TODO: create an IntPredicate that checks divisibility by 3
        IntPredicate divisibleBy3 = x -> x % 3 == 0;

        List<Integer> results = null;
        // TODO: use
        results = IntStream.generate(randomSupplier).filter(divisibleBy3).limit(10).boxed().toList();

        System.out.println("10 random multiples of 3 (0-99): " + results);
    }

    // Exercise 5: Performance comparison
    // TODO: Time both approaches over 10 million integers:
    // A) Stream<Integer>.reduce(0, Integer::sum) using Stream.iterate()
    // B) IntStream.rangeClosed().reduce(0, Integer::sum)
    // Print both elapsed times and calculate the speedup factor
    public static void exercise5() {
        System.out.println("\n=== Exercise 5: Performance comparison (10M integers) ===");

        long startBoxed = System.nanoTime();
        // TODO: implement boxed reduce using Stream.iterate(1, n -> n +
        // 1).limit(10_000_000).reduce(0, Integer::sum)
        int sumBoxed = Stream.iterate(1, n -> n + 1).limit(10_000_000).reduce(0, Integer::sum);
        long elapsedBoxed = System.nanoTime() - startBoxed;

        long startPrimitive = System.nanoTime();
        // TODO: implement primitive reduce using IntStream.rangeClosed(1,
        // 10_000_000).reduce(0, Integer::sum)
        int sumPrimitive = IntStream.rangeClosed(1, 10_000_000).reduce(0, Integer::sum);
        long elapsedPrimitive = System.nanoTime() - startPrimitive;

        double speedup = (double) elapsedBoxed / elapsedPrimitive;

        System.out.println("Boxed reduce:      " + sumBoxed + " (" + elapsedBoxed / 1_000_000 + " ms)");
        System.out.println("Primitive reduce:  " + sumPrimitive + " (" + elapsedPrimitive / 1_000_000 + " ms)");
        System.out.println("Speedup factor:    " + String.format("%.1fx", speedup));
    }
}
