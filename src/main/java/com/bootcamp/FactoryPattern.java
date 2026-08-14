package com.bootcamp;

import java.util.*;

/**
 * Exercise: Type-Safe Factory Pattern Using Sealed Hierarchies and Switch Expressions
 *
 * <p>Implement the methods below using: - Sealed interfaces/classes for type-safe return types -
 * Pattern matching for switch expressions - Record patterns for deconstruction - Factory methods
 * with sealed hierarchies - Algebraic data types for domain modeling - Exhaustive switch
 * expressions for handling all cases
 */
public class FactoryPattern {

  // TODO 1: Define a sealed interface Shape with record implementations for Circle, Rectangle,
  //         and Triangle. Each record should store relevant dimensions.
  //         Implement a factory method createShape(String type, double... dimensions)
  //         that returns the appropriate Shape based on the type string.
  //         Use pattern matching for switch to handle each case.
  public sealed interface Shape permits Circle, Rectangle, Triangle {}

  // TODO 2: Create record implementations for Circle (radius), Rectangle (width, height),
  //         and Triangle (base, height) that implement Shape.
  //         Each record should have a compact constructor that validates dimensions are positive.
  //         Throw IllegalArgumentException for invalid dimensions.

  public static Shape createShape(String type, double... dimensions) {
    throw new UnsupportedOperationException("Implement TODO 1");
  }

  // TODO 3: Implement a method calculateArea(Shape shape) that uses pattern matching for switch
  //         to calculate the area of each shape type.
  //         Circle: π * radius²
  //         Rectangle: width * height
  //         Triangle: 0.5 * base * height

  public static double calculateArea(Shape shape) {
    throw new UnsupportedOperationException("Implement TODO 3");
  }

  // TODO 4: Create a sealed interface Payment with record implementations for
  //         CreditCard (cardNumber, amount), PayPal (email, amount),
  //         and BankTransfer (accountNumber, amount).
  //         Implement a factory method createPayment(String type, double amount, String... details)
  //         that returns the appropriate Payment type.

  public sealed interface Payment permits CreditCard, PayPal, BankTransfer {}

  public static Payment createPayment(String type, double amount, String... details) {
    throw new UnsupportedOperationException("Implement TODO 4");
  }

  // TODO 5: Implement a method processPayment(Payment payment) that uses pattern matching
  //         for switch to return a String description of how each payment type is processed.
  //         Use record patterns to deconstruct each payment type.

  public static String processPayment(Payment payment) {
    throw new UnsupportedOperationException("Implement TODO 5");
  }

  // TODO 6: Create a sealed interface ValidationResult with record implementations for
  //         Valid (value) and Invalid (errors: List<String>).
  //         Implement a factory method validate(String input, List<String> rules)
  //         that applies all rules and returns either Valid or Invalid.
  //         Use a rule-based validation approach with BiFunction predicates.

  public sealed interface ValidationResult permits Valid, Invalid {}

  public static ValidationResult validate(String input, List<String> rules) {
    throw new UnsupportedOperationException("Implement TODO 6");
  }

  // TODO 7: Implement a method formatResult(ValidationResult result) that uses
  //         pattern matching for switch to format the result:
  //         Valid: "Valid: [value]"
  //         Invalid: "Invalid: [error1, error2, ...]"

  public static String formatResult(ValidationResult result) {
    throw new UnsupportedOperationException("Implement TODO 7");
  }

  // TODO 8: Create a sealed interface Command with record implementations for
  //         Move (x, y coordinates), Delete (id), and Update (id, newData).
  //         Implement a factory method that creates commands from a Map<String, Object>.

  public sealed interface Command permits Move, Delete, Update {}

  public static Command createCommand(Map<String, Object> params) {
    throw new UnsupportedOperationException("Implement TODO 8");
  }

  // TODO 9: Implement a method executeCommand(Command command) that uses pattern matching
  //         for switch to return the action taken:
  //         Move: "Moving to (x, y)"
  //         Delete: "Deleting item [id]"
  //         Update: "Updating item [id] with [newData]"

  public static String executeCommand(Command command) {
    throw new UnsupportedOperationException("Implement TODO 9");
  }

  // TODO 10: Create a sealed interface GameEvent with record implementations for
  //          PlayerJoined (playerName), PlayerLeft (playerName), and ScoreChanged (playerName, newScore).
  //          Implement a method that processes a List<GameEvent> and returns a Map<String, Integer>
  //          representing current player scores (handling join, leave, and score changes).

  public sealed interface GameEvent permits PlayerJoined, PlayerLeft, ScoreChanged {}

  public static Map<String, Integer> processGameEvents(List<GameEvent> events) {
    throw new UnsupportedOperationException("Implement TODO 10");
  }

  public static void main(String[] args) {
    System.out.println("=== Type-Safe Factory Pattern Using Sealed Hierarchies ===");
    System.out.println();

    System.out.println("TODO 1 & 2 - Shape Factory:");
    Shape circle = createShape("circle", 5.0);
    Shape rect = createShape("rectangle", 4.0, 6.0);
    Shape tri = createShape("triangle", 3.0, 8.0);
    System.out.println("Circle created: " + circle);
    System.out.println("Rectangle created: " + rect);
    System.out.println("Triangle created: " + tri);
    System.out.println();

    System.out.println("TODO 3 - Calculate Area:");
    System.out.println("Circle area: " + calculateArea(circle));
    System.out.println("Rectangle area: " + calculateArea(rect));
    System.out.println("Triangle area: " + calculateArea(tri));
    System.out.println();

    System.out.println("TODO 4 & 5 - Payment Factory:");
    Payment creditCard = createPayment("creditcard", 99.99, "1234-5678-9012-3456");
    Payment paypal = createPayment("paypal", 49.99, "user@example.com");
    Payment bankTransfer = createPayment("banktransfer", 199.99, "ACC123456");
    System.out.println(processPayment(creditCard));
    System.out.println(processPayment(paypal));
    System.out.println(processPayment(bankTransfer));
    System.out.println();

    System.out.println("TODO 6 & 7 - Validation Factory:");
    ValidationResult valid = validate("hello", List.of(s -> !s.isEmpty(), s -> s.length() > 2));
    ValidationResult invalid = validate("", List.of(s -> !s.isEmpty(), s -> s.length() > 2));
    System.out.println(formatResult(valid));
    System.out.println(formatResult(invalid));
    System.out.println();

    System.out.println("TODO 8 & 9 - Command Factory:");
    Command move = createCommand(Map.of("type" -> "move", "x" -> 10, "y" -> 20));
    Command delete = createCommand(Map.of("type" -> "delete", "id" -> 42));
    Command update = createCommand(Map.of("type" -> "update", "id" -> 7, "newData" -> "updated"));
    System.out.println(executeCommand(move));
    System.out.println(executeCommand(delete));
    System.out.println(executeCommand(update));
    System.out.println();

    System.out.println("TODO 10 - Game Events:");
    List<GameEvent> events = List.of(
        new GameEvent.PlayerJoined("Alice"),
        new GameEvent.ScoreChanged("Alice", 100),
        new GameEvent.PlayerJoined("Bob"),
        new GameEvent.ScoreChanged("Bob", 50),
        new GameEvent.ScoreChanged("Alice", 150)
    );
    System.out.println("Final scores: " + processGameEvents(events));
  }
}
