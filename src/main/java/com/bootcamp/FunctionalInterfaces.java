package com.bootcamp;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalInterfaces {

    public static void main(String[] args) {

        // Exercise 1: Supplier<T> — Lazy initialization
        // A Supplier<T> takes no arguments and returns a value of type T.
        // TODO: Create a Supplier<String> that returns a greeting with the current
        // timestamp
        // Use it with System.out.println() to demonstrate lazy evaluation
        Supplier<String> greetingSupplier = () -> "Hello! Current time: " + System.currentTimeMillis();
        System.out.println(greetingSupplier.get()); // This will print the greeting with the current timestamp

        // Exercise 2: Predicate<T> — Chaining with and(), or(), negate()
        // TODO: Given the list below, filter numbers that are:
        // - Greater than 2 AND
        // - Less than 10 AND
        // - NOT equal to 5
        // Use Predicate.and() and Predicate.negate() to compose the conditions
        List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        Predicate<Integer> greaterThan2 = x -> x > 2;
        Predicate<Integer> lessThan10 = x -> x < 10;
        Predicate<Integer> notEqualTo5 = x -> x != 5;
        values.stream()
                .filter(greaterThan2.and(lessThan10).and(notEqualTo5))
                .forEach(System.out::println); // This will print 3, 4, 6, 7, 8, 9

        // Exercise 3: Function<T,R> — Composition with andThen() and compose()
        // TODO: Create two functions:
        // - one that doubles an integer: x -> x * 2
        // - one that adds 10: x -> x + 10
        // Then compose them using andThen() so the result is: (x * 2) + 10
        // And compose them using compose() so the result is: (x + 10) * 2
        // Print both results for input value 5
        Function<Integer, Integer> doubleFunction = x -> x * 2;
        Function<Integer, Integer> addTenFunction = x -> x + 10;
        Function<Integer, Integer> doubleThenAddTen = doubleFunction.andThen(addTenFunction);
        Function<Integer, Integer> addTenThenDouble = doubleFunction.compose(addTenFunction);
        System.out.println(doubleThenAddTen.apply(5)); // This will print 20 ((5 * 2) + 10)
        System.out.println(addTenThenDouble.apply(5)); // This will print 30 ((5 + 10) * 2)

        // Exercise 4: Consumer<T> — Chaining with andThen()
        // TODO: Create two consumers for String:
        // - one that converts to uppercase and prints
        // - one that wraps in brackets and prints
        // Chain them with andThen() and apply to the list ["hello", "world"]
        Consumer<String> toUpperCaseConsumer = s -> System.out.println(s.toUpperCase());
        Consumer<String> bracketConsumer = s -> System.out.println("[" + s + "]");
        Consumer<String> combinedConsumer = toUpperCaseConsumer.andThen(bracketConsumer);
        List<String> words = List.of("hello", "world");
        words.forEach(combinedConsumer); // This will print "HELLO" and "[hello]", then "WORLD" and "[world]"

        // Exercise 5: Supplier<T> with Optional — Lazy fallback
        // TODO: Simulate a "maybe missing" value using Optional.empty().
        // Use a Supplier with orElseGet() to generate a default value lazily.
        // Print the result
        Supplier<String> defaultValueSupplier = () -> "Default Value";
        String result = Optional.<String>empty().orElseGet(defaultValueSupplier);
        System.out.println(result); // This will print "Default Value"

    }
}
