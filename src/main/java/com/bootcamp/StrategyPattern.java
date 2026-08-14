package com.bootcamp;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Exercise: Functional Strategy Pattern Leveraging Native Lambdas
 *
 * <p>Implement the methods below using: - Functional interfaces as strategy definitions - Lambda
 * expressions as strategy implementations - Method references for simple strategies - Closures for
 * stateful strategies - Strategy composition with and/or/chaining - Predicate-based filtering
 * strategies - Function-based transformation strategies - Comparator-based sorting strategies -
 * Real-world pattern: pricing, validation, and transformation pipelines
 */
public class StrategyPattern {

  // TODO 1: Define a pricing strategy using BiFunction<Double, Double, Double>.
  //         The strategy takes (basePrice, quantity) and returns the final price.
  //         Implement three strategies: "normal" (no discount), "bulk" (10% off if qty >= 10),
  //         "premium" (20% off always).
  //         Return a list of final prices for each strategy applied to (basePrice=100.0, qty=15).
  public static List<Double> pricingStrategies(double basePrice, int quantity) {
    BiFunction<Double, Integer, Double> normal = (x, y) -> x * y;
    BiFunction<Double, Integer, Double> bulk = (x, y) -> y >= 10 ? x * y * 0.9 : x * y;
    BiFunction<Double, Integer, Double> premium = (x, y) -> x * y * 0.8;
    return List.of(
        normal.apply(basePrice, quantity),
        bulk.apply(basePrice, quantity),
        premium.apply(basePrice, quantity));
  }

  // TODO 2: Create a reusable strategy combiner using BinaryOperator<Predicate<String>>.
  //         Given two predicates (p1 and p2), combine them with logical AND.
  //         Use this to build a validator that checks:
  //         - String is not null AND length >= 3
  //         Apply this combined predicate to filter a list of strings.
  //         Example: ["a", "ab", "abc", "abcd", null] -> ["abc", "abcd"]
  public static List<String> filterWithCombinedPredicate(List<String> words) {
    BinaryOperator<Predicate<String>> combiner = Predicate::and;
    List<Predicate<String>> rules = List.of(Objects::nonNull, w -> w.length() >= 3);
    Predicate<String> combined =
        rules.stream().reduce(combiner).orElse(w -> true); // default: accept all

    return words.stream().filter(combined).toList();
  }

  // TODO 3: Implement a strategy pipeline using Function composition.
  //         Given three Function<String, String> strategies:
  //         - trim: removes leading/trailing whitespace
  //         - lowercase: converts to lowercase
  //         - reverse: reverses the string
  //         Compose them into a single pipeline: trim -> lowercase -> reverse.
  //         Apply to each word in the list.
  //         Example: ["  Hello ", " WORLD "] -> ["olleh", "dlrow"]
  public static List<String> composeStringPipeline(List<String> words) {
    Function<String, String> f1 = String::trim;
    Function<String, String> f2 = String::toLowerCase;
    Function<String, String> f3 = s -> new StringBuilder(s).reverse().toString();
    Function<String, String> pipeline = f1.andThen(f2).andThen(f3);
    return words.stream().map(pipeline).toList();
  }

  // TODO 4: Use a strategy map (Map<String, Function>) to dispatch transformations.
  //         Given a map of transformation names to functions:
  //         - "upper": String::toUpperCase
  //         - "lower": String::toLowerCase
  //         - "length": s -> String.valueOf(s.length())
  //         Apply the appropriate transformation to each word based on a list of strategy names.
  //         Example: words=["Hello","World"], strategies=["upper","length"] -> ["HELLO","5"]
  public static List<String> dispatchTransformations(
      List<String> words, List<String> strategyNames) {
    Function<String, String> f1 = String::toUpperCase;
    Function<String, String> f2 = String::toLowerCase;
    Function<String, String> f3 = s -> String.valueOf(s.length());
    Map<String, Function<String, String>> funcs = new HashMap<>();
    funcs.put("upper", f1);
    funcs.put("lower", f2);
    funcs.put("length", f3);
    return IntStream.range(0, words.size())
        .mapToObj(
            i -> {
              Function<String, String> fn = funcs.get(strategyNames.get(i));
              return fn.apply(words.get(i));
            })
        .toList();
  }

  // TODO 5: Implement a sorting strategy using Comparator composition.
  //         Given a list of Person records (name, age, score),
  //         sort by: age ascending, then score descending for ties.
  //         Use Comparator.comparingInt() and .reversed().
  //         Return the sorted list.
  public record Person(String name, int age, double score) {}

  public static List<Person> sortByAgeThenScore(List<Person> people) {
    return people.stream()
        .sorted(
            Comparator.comparingInt(Person::age)
                .thenComparing(Comparator.comparing(Person::score).reversed()))
        .toList();
  }

  // TODO 6: Create a strategy for conditional execution using Predicate + Consumer.
  //         Given a Predicate<Integer> condition and a list of numbers,
  //         apply different actions: if even, add to "evens" list; if odd, add to "odds" list.
  //         Use collect(Collectors.partitioningBy()) as an alternative approach.
  //         Return a Map<Boolean, List<Integer>> with true=even, false=odd.
  public static Map<Boolean, List<Integer>> partitionByPredicate(List<Integer> numbers) {
    Predicate<Integer> isEven = i -> i % 2 == 0;
    return numbers.stream().collect(Collectors.partitioningBy(isEven));
  }

  // TODO 7: Implement a validation strategy chain using BiFunction<String, String, Boolean>.
  //         Given a list of validation rules (each a BiFunction<email, ruleName, Boolean>),
  //         chain them so that ALL must pass for the email to be valid.
  //         Return a Predicate<String> that encapsulates all rules.
  //         Apply this predicate to filter valid emails.
  //         Rules: "notNull" (not null), "hasAt" (contains @), "hasDomain" (contains .)
  public static List<String> validateEmails(List<String> emails) {
    BinaryOperator<Predicate<String>> combiner = Predicate::and;
    BiFunction<String, String, Boolean> validator = (email, ruleName) ->
      switch(ruleName) {
        case "notNull" -> email != null;
        case "hasAt" -> email.contains("@");
        case "hasDomain" -> email.contains(".");
        default -> false;
    };
    Predicate<String> validateNotNull = s -> validator.apply(s, "notNull");
    Predicate<String> validateHasAt = s -> validator.apply(s, "hasAt");
    Predicate<String> validateHasDomain = s -> validator.apply(s, "hasDomain");

    List<Predicate<String>> rules = List.of(validateNotNull, validateHasAt, validateHasDomain);
    Predicate<String> combined =
            rules.stream().reduce(combiner).orElse(w -> true); // default: accept all

    return emails.stream().filter(combined).toList();
  }

  // TODO 8: Use UnaryOperator<T> as a self-transformation strategy.
  //         Given a list of integers, apply a "doubler" strategy that doubles each value.
  //         Then apply a "clamper" strategy that clamps values to max 100.
  //         Compose both: first double, then clamp.
  //         Example: [50, 60, 80, 120] -> [100, 100, 100, 100]
  public static List<Integer> composeUnaryOperators(List<Integer> numbers) {
    Function<Integer, Integer> doubler = n -> n * 2;
    Function<Integer, Integer> clamper = n -> Math.min(n, 100);
    Function<Integer, Integer> pipeline = doubler.andThen(clamper);
    return numbers.stream().map(pipeline).toList();
  }

  // TODO 9: Implement a strategy pattern with an accumulator.
  //         Given a BinaryOperator<Integer> accumulator strategy,
  //         reduce a list using that strategy.
  //         Test with: max strategy, min strategy, sum strategy.
  //         Return a Map<String, Integer> with keys "max", "min", "sum".
  public static Map<String, Integer> reduceWithStrategies(List<Integer> numbers) {
    BinaryOperator<Integer> maxStrategy = BinaryOperator.maxBy(Integer::compareTo);
    BinaryOperator<Integer> minStrategy = BinaryOperator.minBy(Integer::compareTo);
    BinaryOperator<Integer> sumStrategy = Integer::sum;
    
    return Map.of(
        "max", numbers.stream().reduce(maxStrategy).orElse(0),
        "min", numbers.stream().reduce(minStrategy).orElse(0),
        "sum", numbers.stream().reduce(0, sumStrategy)
    );
  }

  // TODO 10: Real-world use case — a discount strategy chain.
  //          Given a base price and a list of discount strategies (each a Function<Double,
  // Double>),
  //          apply all discounts sequentially (each receives the output of the previous).
  //          Example: base=100.0, discounts=[0.9 (10% off), 0.8 (20% off)] -> 72.0
  //          Return the final price after all discounts.
  public static double applyDiscountChain(
      double basePrice, List<Function<Double, Double>> discounts) {
    return discounts.stream()
        .reduce(Function.identity(), Function::andThen)
        .apply(basePrice);
  }

  public static void main(String[] args) {
    System.out.println("=== Functional Strategy Pattern Leveraging Native Lambdas ===");
    System.out.println();

    System.out.println("TODO 1 - Pricing Strategies:");
    System.out.println(pricingStrategies(100.0, 15));
    System.out.println();

    List<String> words = Arrays.asList("a", "ab", "abc", "abcd", null, "abcde");
    System.out.println("TODO 2 - Filter With Combined Predicate:");
    System.out.println(filterWithCombinedPredicate(words));
    System.out.println();

    List<String> messy = List.of("  Hello ", " WORLD ", "  Java  ");
    System.out.println("TODO 3 - Compose String Pipeline:");
    System.out.println(composeStringPipeline(messy));
    System.out.println();

    List<String> toTransform = List.of("Hello", "World", "Java");
    List<String> strategies = List.of("upper", "length", "lower");
    System.out.println("TODO 4 - Dispatch Transformations:");
    System.out.println(dispatchTransformations(toTransform, strategies));
    System.out.println();

    List<Person> people =
        List.of(
            new Person("Alice", 30, 95.0),
            new Person("Bob", 25, 88.0),
            new Person("Charlie", 30, 92.0),
            new Person("Diana", 25, 95.0));
    System.out.println("TODO 5 - Sort By Age Then Score:");
    System.out.println(sortByAgeThenScore(people));
    System.out.println();

    List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println("TODO 6 - Partition By Predicate:");
    System.out.println(partitionByPredicate(nums));
    System.out.println();

    List<String> emails =
            Arrays.asList("alice@example.com", "bob@", "charlie", "diana@test.org", null, "eve@valid.io");
    System.out.println("TODO 7 - Validate Emails:");
    System.out.println(validateEmails(emails));
    System.out.println();

    List<Integer> values = List.of(50, 60, 80, 120, 30);
    System.out.println("TODO 8 - Compose Unary Operators:");
    System.out.println(composeUnaryOperators(values));
    System.out.println();

    System.out.println("TODO 9 - Reduce With Strategies:");
    System.out.println(reduceWithStrategies(nums));
    System.out.println();

    List<Function<Double, Double>> discounts =
        List.of(price -> price * 0.9, price -> price * 0.8, price -> price - 5.0);
    System.out.println("TODO 10 - Apply Discount Chain:");
    System.out.println(applyDiscountChain(100.0, discounts));
  }
}
