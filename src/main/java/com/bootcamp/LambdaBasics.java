package com.bootcamp;

import java.util.List;
import java.util.function.Consumer;

public class LambdaBasics {

  public static void main(String[] args) {

    // Exercise 1: Print each number in the list using a lambda with Consumer<Integer>
    List<Integer> numbers = List.of(1, 2, 3, 4, 5);
    // TODO: use numbers.forEach() with a lambda that prints each number
    numbers.forEach(number -> System.out.println(number));

    // Exercise 2: Filter only even numbers using a lambda with Predicate<Integer>
    // TODO: use a lambda to check if a number is even
    numbers.stream().filter(number -> number % 2 == 0).forEach(System.out::println);

    // Exercise 3: Convert strings to uppercase using a lambda with Function<String, String>
    // TODO: use a lambda to transform a string to uppercase
    List<String> strings = List.of("a", "b", "c");
    strings.stream().map(string -> string.toUpperCase()).forEach(System.out::println);

    // Exercise 4: Effectively final variable scoping
    String prefix = "Item: ";
    // TODO: Uncomment the line below and explain why it compiles (or doesn't)
    Consumer<String> printer = item -> System.out.println(prefix + item);
    // prefix = "Updated: ";
    printer.accept("Cenas");
  }
}
