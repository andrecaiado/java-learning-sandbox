package com.bootcamp;

public class CustomFunctionalInterfaces {

  // Exercise 1: Define a custom @FunctionalInterface
  // TODO: Create a @FunctionalInterface called "Parser<T>" with a single abstract
  // method:
  // T parse(String input) throws IllegalArgumentException
  @FunctionalInterface
  public interface Parser<T> {
    T parse(String input) throws IllegalArgumentException;
  }

  public static void main(String[] args) {
    exercise2();
    exercise5();
  }

  // Exercise 2: Use your custom interface with a lambda
  // TODO: Create a Parser<Integer> that parses strings to integers using
  // Integer.parseInt()
  // Use it with System.out.println() to parse "42"
  public static void exercise2() {
    Parser<Integer> p = s -> Integer.parseInt(s);
    System.out.println(p.parse("42"));
  }

  // Exercise 3: Prove @FunctionalInterface enforcement
  // TODO: Uncomment the second abstract method below and observe the compile
  // error
  // Then comment it back out to proceed
  /*
   * @FunctionalInterface
   * interface BrokenParser<T> {
   * T parse(String input);
   * String format(T value); // This causes a compile error!
   * }
   */

  // Exercise 4: default and static methods are allowed
  // TODO: Create a @FunctionalInterface called "Validator<T>" with:
  // - abstract method: boolean validate(T value)
  // - default method: and(Validator<T> other) that chains two validators with &&
  // - static method: notEmpty() that returns a Validator<String> checking
  // non-empty
  @FunctionalInterface
  public interface Validator<T> {
    boolean validate(T value);

    default Validator<T> and(Validator<T> other) {
      return value -> this.validate(value) && other.validate(value);
    }

    static Validator<String> notEmpty() {
      return value -> value.isEmpty();
    }
  }

  // Exercise 5: Build a practical validation pipeline
  // TODO: Using your Validator<T> interface from Exercise 4:
  // - Create a Validator<String> that checks length >= 3
  // - Create a Validator<String> that checks contains no spaces
  // - Chain them with and()
  // - Test with "hello", "hi", "no spaces", and print results
  public static void exercise5() {
    Validator<String> minLength3 = s -> s.length() >= 3;
    Validator<String> noSpaces = s -> !s.contains(" ");
    Validator<String> combined = minLength3.and(noSpaces);
    System.out.println(combined.validate("hello"));
    System.out.println(combined.validate("hi"));
    System.out.println(combined.validate("no spaces"));
  }
}
