package com.bootcamp;

public class PatternMatchingSwitch {

  public static void main(String[] args) {
    exercise1();
    exercise2();
    exercise3();
    exercise4();
  }

  // Exercise 1: Object type patterns with when guards
  // TODO: Use 'when' guards to filter objects based on their values.
  //
  // Method: classifyString(String s)
  //   - case String s when s.isEmpty()        -> "Empty string"
  //   - case String s when s.length() < 5     -> "Short: " + s
  //   - case String s when s.startsWith("J")  -> "Java-related: " + s
  //   - case String s                         -> "Other string: " + s
  //   - case null                             -> "Null input"
  //
  // The 'when' clause adds a boolean guard after the pattern.
  // Cases are checked top-to-bottom — order matters!
  public static void exercise1() {
    System.out.println("=== Exercise 1: Object type patterns with when guards ===");

    System.out.println(classifyString(""));
    System.out.println(classifyString("hi"));
    System.out.println(classifyString("Java"));
    System.out.println(classifyString("Hello World"));
    System.out.println(classifyString(null));
  }

  // Exercise 2: Nested record patterns with when guards
  // TODO: Deconstruct nested records and apply guards on inner values.
  //
  // Records:
  //   - Item(String name, double price, int quantity)
  //   - Box(Item item, String destination, boolean isFragile)
  //
  // Method: describeShipment(Box box)
  //   - case Box(Item(String n, double p, int q), String dest, boolean frag)
  //       when q > 10 && !frag
  //       -> "Bulk shipment: " + q + "x " + n + " to " + dest
  //   - case Box(Item(String n, double p, int q), String dest, boolean frag)
  //       when p * q > 100
  //       -> "High-value shipment: $" + (p * q) + " to " + dest
  //   - case Box(Item(String n, double p, int q), String dest, boolean frag)
  //       when frag
  //       -> "Fragile shipment: " + n + " to " + dest
  //   - case Box(Item(String n, double p, int q), String dest, boolean frag)
  //       -> "Standard shipment: " + n + " to " + dest
  //   - case null -> "No shipment"
  //   - default   -> "Unknown shipment type"
  //
  // Nested patterns destructure multiple record levels in one expression.
  // Guards inspect inner record fields to classify shipments by business rules.
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Nested record patterns with when guards ===");

    System.out.println(describeShipment(new Box(new Item("Widget", 2.5, 20), "New York", false)));
    System.out.println(describeShipment(new Box(new Item("Laptop", 999.0, 1), "Tokyo", true)));
    System.out.println(describeShipment(new Box(new Item("Book", 15.0, 3), "London", true)));
    System.out.println(describeShipment(new Box(new Item("Pen", 1.0, 5), "Paris", false)));
    System.out.println(describeShipment(null));
  }

  // Exercise 3: Nested record patterns with when guards
  // TODO: Combine record deconstruction with value-based guards.
  //
  // Records:
  //   - Point(int x, int y)
  //   - Rectangle(Point origin, Point corner)
  //   - Circle(Point center, double radius)
  //
  // Method: describeShape(Object shape)
  //   - case Circle(Point p, double r) when r <= 0
  //       -> "Invalid circle radius"
  //   - case Circle(Point p, double r) when p.x() == 0 && p.y() == 0
  //       -> "Circle at origin, radius " + r
  //   - case Circle(Point p, double r)
  //       -> "Circle at (" + p.x() + "," + p.y() + "), radius " + r
  //   - case Rectangle(Point a, Point b) when a.x() == b.x() || a.y() == b.y()
  //       -> "Degenerate rectangle (zero area)"
  //   - case Rectangle(Point a, Point b)
  //       -> "Rectangle from (" + a.x() + "," + a.y() + ") to (" + b.x() + "," + b.y() + ")"
  //   - case null -> "Null shape"
  //   - default   -> "Unknown shape type"
  //
  // Nested patterns destructure multiple levels in one expression.
  // Guards validate constraints that the type system cannot enforce.
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Nested record patterns with when guards ===");

    System.out.println(describeShape(new Circle(new Point(0, 0), 5.0)));
    System.out.println(describeShape(new Circle(new Point(3, 4), 2.5)));
    System.out.println(describeShape(new Circle(new Point(1, 1), -1.0)));
    System.out.println(describeShape(new Rectangle(new Point(0, 0), new Point(5, 5))));
    System.out.println(describeShape(new Rectangle(new Point(2, 3), new Point(2, 7))));
    System.out.println(describeShape(null));
  }

  // Exercise 4: Complex dispatch with sealed-style type hierarchy
  // TODO: Build a type-safe evaluator for different payment methods.
  //
  // Sealed interface (simulated with records for this exercise):
  //   - CreditCard(String number, int expiryMonth, int expiryYear)
  //   - BankTransfer(String routingNumber, String accountNumber)
  //   - DigitalWallet(String provider, double balance)
  //
  // Method: validatePayment(Payment payment)
  //   - case CreditCard(String num, int month, int year)
  //       when num.length() == 16 && month >= 1 && month <= 12 && year >= 2024
  //       -> "Valid credit card ending " + num.substring(12)
  //   - case CreditCard
  //       -> "Invalid credit card details"
  //   - case BankTransfer(String routing, String account)
  //       when routing.length() == 9 && account.length() >= 8
  //       -> "Valid bank transfer to account " + account.substring(account.length() - 4)
  //   - case BankTransfer
  //       -> "Invalid bank transfer details"
  //   - case DigitalWallet(String provider, double balance)
  //       when balance > 0 && (provider.equals("PayPal") || provider.equals("Venmo"))
  //       -> "Valid " + provider + " wallet with $" + balance
  //   - case DigitalWallet
  //       -> "Invalid digital wallet"
  //   - case null -> "Null payment method"
  //   - default   -> "Unknown payment type"
  //
  // This exercise demonstrates real-world validation logic using pattern guards.
  // Each case validates both type AND business constraints simultaneously.
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Complex dispatch with payment validation ===");

    System.out.println(validatePayment(new CreditCard("1234567890123456", 12, 2026)));
    System.out.println(validatePayment(new CreditCard("1234", 13, 2023)));
    System.out.println(validatePayment(new BankTransfer("123456789", "12345678")));
    System.out.println(validatePayment(new BankTransfer("123", "1234")));
    System.out.println(validatePayment(new DigitalWallet("PayPal", 50.0)));
    System.out.println(validatePayment(new DigitalWallet("Bitcoin", 100.0)));
    System.out.println(validatePayment(null));
  }

  // =============================================
  // TODO: Implement the methods below
  // =============================================

  private static String classifyString(String s) {
    return switch (s) {
      case String str when str.isEmpty() -> "Empty string";
      case String str when str.length() < 5 -> "Short: " + str;
      case String str when str.startsWith("J") -> "Java-related: " + str;
      case String str -> "Other string: " + str;
      case null -> "Null input";
    };
  }

  private static String describeShipment(Box box) {
    // TODO: Implement using nested record patterns with when guards
    return ""; // Replace with your implementation
  }

  private static String describeShape(Object shape) {
    return switch (shape) {
      case Circle(Point p, double r) when r <= 0 -> "Invalid circle radius";
      case Circle(Point p, double r) when p.x() == 0 && p.y() == 0 ->
          "Circle at origin, radius " + r;
      case Circle(Point p, double r) -> "Circle at (" + p.x() + "," + p.y() + "), radius " + r;
      case Rectangle(Point a, Point b) when a.x() == b.x() || a.y() == b.y() ->
          "Degenerate rectangle (zero area)";
      case Rectangle(Point a, Point b) ->
          "Rectangle from (" + a.x() + "," + a.y() + ") to (" + b.x() + "," + b.y() + ")";
      case null -> "Null shape";
      default -> "Unknown shape type";
    };
  }

  private static String validatePayment(Payment payment) {
    return switch (payment) {
      case CreditCard(String num, int month, int year)
          when num.length() == 16 && month >= 1 && month <= 12 && year >= 2024 ->
          "Valid credit card ending " + num.substring(12);
      case CreditCard ignored -> "Invalid credit card details";
      case BankTransfer(String routing, String account)
          when routing.length() == 9 && account.length() >= 8 ->
          "Valid bank transfer to account " + account.substring(account.length() - 4);
      case BankTransfer ignored -> "Invalid bank transfer details";
      case DigitalWallet(String provider, double balance)
          when balance > 0 && (provider.equals("PayPal") || provider.equals("Venmo")) ->
          "Valid " + provider + " wallet with $" + balance;
      case DigitalWallet ignored -> "Invalid digital wallet";
      case null -> "Null payment method";
    };
  }

  // =============================================
  // TODO: Create your records and interfaces below
  // =============================================

  // Exercise 2: Shipment records
  public record Item(String name, double price, int quantity) {}

  public record Box(Item item, String destination, boolean isFragile) {}

  // Exercise 3: Geometric shapes
  public record Point(int x, int y) {}

  public record Circle(Point center, double radius) {}

  public record Rectangle(Point origin, Point corner) {}

  // Exercise 4: Payment methods (simulating sealed hierarchy)
  public sealed interface Payment {}

  public record CreditCard(String number, int expiryMonth, int expiryYear) implements Payment {}

  public record BankTransfer(String routingNumber, String accountNumber) implements Payment {}

  public record DigitalWallet(String provider, double balance) implements Payment {}
}
