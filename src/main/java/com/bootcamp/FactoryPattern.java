package com.bootcamp;

import java.util.*;
import java.util.function.Predicate;

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
    return switch (type) {
      case "circle" -> {
        if (dimensions.length < 1) throw new IllegalArgumentException(("Need 1 dimension"));
        yield new Circle(dimensions[0]);
      }
      case "rectangle" -> {
        if (dimensions.length < 2) throw new IllegalArgumentException(("Need 2 dimensions"));
        yield new Rectangle(dimensions[0], dimensions[1]);
      }
      case "triangle" -> {
        if (dimensions.length < 2) throw new IllegalArgumentException(("Need 2 dimensions"));
        yield new Triangle(dimensions[0], dimensions[1]);
      }
      default -> throw new IllegalArgumentException("Invalid shape");
    };
  }

  public record Circle(double radius) implements Shape {
    public Circle {
      if (radius <= 0) {
        throw new IllegalArgumentException("Dimensions must be positive");
      }
    }
  }

  public record Rectangle(double width, double height) implements Shape {
    public Rectangle {
      if (width <= 0 || height <= 0) {
        throw new IllegalArgumentException("Dimensions must be positive");
      }
    }
  }

  public record Triangle(double base, double height) implements Shape {
    public Triangle {
      if (base <= 0 || height <= 0) {
        throw new IllegalArgumentException("Dimensions must be positive");
      }
    }
  }

  // TODO 3: Implement a method calculateArea(Shape shape) that uses pattern matching for switch
  //         to calculate the area of each shape type.
  //         Circle: π * radius²
  //         Rectangle: width * height
  //         Triangle: 0.5 * base * height

  public static double calculateArea(Shape shape) {
    return switch (shape) {
      case Circle(double radius) -> Math.PI * (radius * radius);
      case Rectangle(double width, double height) -> width * height;
      case Triangle(double base, double height) -> 0.5 * base * height;
    };
  }

  // TODO 4: Create a sealed interface Payment with record implementations for
  //         CreditCard (cardNumber, amount), PayPal (email, amount),
  //         and BankTransfer (accountNumber, amount).
  //         Implement a factory method createPayment(String type, double amount, String... details)
  //         that returns the appropriate Payment type.

  public sealed interface Payment permits CreditCard, PayPal, BankTransfer {}

  public static Payment createPayment(String type, double amount, String... details) {
    if (details.length < 1) throw new IllegalArgumentException("Payment requires 1 detail");
    return switch (type) {
      case "creditcard" -> new CreditCard(details[0], amount);
      case "paypal" -> new PayPal(details[0], amount);
      case "banktransfer" -> new BankTransfer(details[0], amount);
      default -> throw new IllegalArgumentException("Invalid payment type");
    };
  }

  public record CreditCard(String cardNumber, Double amount) implements Payment {}

  public record PayPal(String email, Double amount) implements Payment {}

  public record BankTransfer(String accountNumber, Double amount) implements Payment {}

  // TODO 5: Implement a method processPayment(Payment payment) that uses pattern matching
  //         for switch to return a String description of how each payment type is processed.
  //         Use record patterns to deconstruct each payment type.

  public static String processPayment(Payment payment) {
    return switch (payment) {
      case CreditCard(String cardNumber, Double amount) -> "Processing payment with Credit Card";
      case PayPal(String email, Double amount) -> "Processing payment with Pay Pal";
      case BankTransfer(String accountNumber, Double amount) ->
          "Processing payment with Bank Transfer";
    };
  }

  // TODO 6: Create a sealed interface ValidationResult with record implementations for
  //         Valid (value) and Invalid (errors: List<String>).
  //         Implement a factory method validate(String input, List<String> rules)
  //         that applies all rules and returns either Valid or Invalid.
  //         Use a rule-based validation approach with BiFunction predicates.

  public sealed interface ValidationResult permits Valid, Invalid {}

  public static ValidationResult validate(String input, List<Predicate<String>> rules) {
    List<String> errors = new ArrayList<>();

    for (int i = 0; i < rules.size(); i++) {
      if (!rules.get(i).test(input)) errors.add("Rule (" + i + ") failed");
    }

    return errors.isEmpty() ? new Valid(input) : new Invalid(errors);
  }

  public record Valid(String value) implements ValidationResult {}

  public record Invalid(List<String> errors) implements ValidationResult {}

  // TODO 7: Implement a method formatResult(ValidationResult result) that uses
  //         pattern matching for switch to format the result:
  //         Valid: "Valid: [value]"
  //         Invalid: "Invalid: [error1, error2, ...]"

  public static String formatResult(ValidationResult result) {
    return switch (result) {
      case Valid(String value) -> "Valid: [" + value + "]";
      case Invalid(List<String> errors) -> "Invalid: " + errors;
    };
  }

  // TODO 8: Create a sealed interface Command with record implementations for
  //         Move (x, y coordinates), Delete (id), and Update (id, newData).
  //         Implement a factory method that creates commands from a Map<String, Object>.

  public sealed interface Command permits Move, Delete, Update {}

  public record Move(Double x, Double y) implements Command {}

  public record Delete(Integer id) implements Command {}

  public record Update(Integer id, String newData) implements Command {}

  public static Command createCommand(Map<String, Object> params) {
    String type = (String) params.get("type");
    return switch (type) {
      case "move" -> new Move((Double) params.get("x"), (Double) params.get("y"));
      case "delete" -> new Delete((Integer) params.get("id"));
      case "update" -> new Update((Integer) params.get("id"), (String) params.get("newData"));
      default -> throw new IllegalStateException("Unexpected value: " + type);
    };
  }

  // TODO 9: Implement a method executeCommand(Command command) that uses pattern matching
  //         for switch to return the action taken:
  //         Move: "Moving to (x, y)"
  //         Delete: "Deleting item [id]"
  //         Update: "Updating item [id] with [newData]"

  public static String executeCommand(Command command) {
    return switch (command) {
      case Move(Double x, Double y) -> "Moving to (" + x + ", " + y + ")";
      case Delete(Integer id) -> "Deleting item [" + id + "]";
      case Update(Integer id, String newData) ->
          "Updating item [" + id + "] with [" + newData + "]";
    };
  }

  // TODO 10: Create a sealed interface GameEvent with record implementations for
  //          PlayerJoined (playerName), PlayerLeft (playerName), and ScoreChanged (playerName,
  // newScore).
  //          Implement a method that processes a List<GameEvent> and returns a Map<String, Integer>
  //          representing current player scores (handling join, leave, and score changes).

  public sealed interface GameEvent permits PlayerJoined, PlayerLeft, ScoreChanged {}

  public record PlayerJoined(String playerName) implements GameEvent {}

  public record PlayerLeft(String playerName) implements GameEvent {}

  public record ScoreChanged(String playerName, Integer newScore) implements GameEvent {}

  public static Map<String, Integer> processGameEvents(List<GameEvent> events) {
    Map<String, Integer> currentPlayerScores = new HashMap<>();
    for (GameEvent event : events) {
      switch (event) {
        case PlayerJoined(String playerName) -> currentPlayerScores.put(playerName, 0);
        case PlayerLeft(String playerName) -> currentPlayerScores.remove(playerName);
        case ScoreChanged(String playerName, Integer newScore) ->
            currentPlayerScores.put(playerName, newScore);
      }
    }
    return currentPlayerScores;
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
    Command move = createCommand(Map.of("type", "move", "x", 10.0, "y", 20.0));
    Command delete = createCommand(Map.of("type", "delete", "id", 42));
    Command update = createCommand(Map.of("type", "update", "id", 7, "newData", "updated"));
    System.out.println(executeCommand(move));
    System.out.println(executeCommand(delete));
    System.out.println(executeCommand(update));
    System.out.println();

    System.out.println("TODO 10 - Game Events:");
    List<GameEvent> events =
        List.of(
            new PlayerJoined("Alice"),
            new ScoreChanged("Alice", 100),
            new PlayerJoined("Bob"),
            new ScoreChanged("Bob", 50),
            new ScoreChanged("Alice", 150));
    System.out.println("Final scores: " + processGameEvents(events));
  }
}
