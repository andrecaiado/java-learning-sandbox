package com.bootcamp;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class FunctionalInterfaces {

    public static void main(String[] args) {

        // Exercise 1: Supplier<T> — Lazy initialization
        // A Supplier<T> takes no arguments and returns a value of type T.
        // TODO: Create a Supplier<String> that returns a greeting with the current timestamp
        //       Use it with System.out.println() to demonstrate lazy evaluation


        // Exercise 2: Predicate<T> — Chaining with and(), or(), negate()
        // TODO: Given the list below, filter numbers that are:
        //       - Greater than 2 AND
        //       - Less than 10 AND
        //       - NOT equal to 5
        //       Use Predicate.and() and Predicate.negate() to compose the conditions
        List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);


        // Exercise 3: Function<T,R> — Composition with andThen() and compose()
        // TODO: Create two functions:
        //       - one that doubles an integer: x -> x * 2
        //       - one that adds 10: x -> x + 10
        //       Then compose them using andThen() so the result is: (x * 2) + 10
        //       And compose them using compose() so the result is: (x + 10) * 2
        //       Print both results for input value 5


        // Exercise 4: Consumer<T> — Chaining with andThen()
        // TODO: Create two consumers for String:
        //       - one that converts to uppercase and prints
        //       - one that wraps in brackets and prints
        //       Chain them with andThen() and apply to the list ["hello", "world"]


        // Exercise 5: Supplier<T> with Optional — Lazy fallback
        // TODO: Simulate a "maybe missing" value using Optional.empty().
        //       Use a Supplier with orElseGet() to generate a default value lazily.
        //       Print the result


    }
}
