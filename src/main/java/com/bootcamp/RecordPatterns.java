package com.bootcamp;

public class RecordPatterns {

  public static void main(String[] args) {
    exercise1();
    exercise2();
    exercise3();
    exercise4();
  }

  // Exercise 1: Basic record pattern in instanceof
  // TODO: Use record patterns to destructure a record inside an instanceof check.
  //
  // Record: Point(int x, int y) — reuse the one from RecordsBasics or redefine
  //
  // Method: describe(Object obj)
  //   - Use: if (obj instanceof Point(int x, int y))
  //   - Return a String like: "Point at (3, 4)" — using the extracted x and y
  //   - Otherwise return: "Not a point"
  //
  // This replaces the old pattern of:
  //   if (obj instanceof Point) { Point p = (Point) obj; ... }
  public static void exercise1() {
    System.out.println("=== Exercise 1: Basic record pattern in instanceof ===");

    System.out.println(describe(new Point(3, 4)));
    System.out.println(describe("hello"));
    System.out.println(describe(new Point(-1, 0)));
  }

  // Exercise 2: Nested record patterns
  // TODO: Destructure a record that contains another record.
  //
  // Records:
  //   - City(String name, int population)
  //   - Country(String name, City capital)
  //
  // Method: describeCapital(Object obj)
  //   - Use nested pattern: obj instanceof Country(String name, City(String capitalName, int pop))
  //   - Return: "The capital of {name} is {capitalName} (pop: {pop})"
  //   - Otherwise return: "Not a country"
  //
  // Think of nested patterns as peeling layers: Country(...) contains City(...),
  // and both are destructured in a single expression.
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Nested record patterns ===");

    City paris = new City("Paris", 2161000);
    Country france = new Country("France", paris);
    System.out.println(describeCapital(france));
    System.out.println(describeCapital("not a country"));
  }

  // Exercise 3: Record patterns with when guards
  // TODO: Add a condition (when clause) to a record pattern.
  //
  // Record: Order(String id, double total, String status)
  //
  // Method: describeOrder(Object obj)
  //   - Use: obj instanceof Order(String id, double total, String status) when total > 100
  //   - If pattern matches AND guard passes: return "Premium order {id}: ${total}"
  //   - If pattern matches but guard fails (total <= 100): return "Regular order {id}: ${total}"
  //   - If not an Order at all: return "Not an order"
  //
  // The 'when' clause adds a boolean guard that must also be true.
  // This is more precise than matching the shape alone.
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Record patterns with when guards ===");

    System.out.println(describeOrder(new Order("ORD-1", 250.0, "SHIPPED")));
    System.out.println(describeOrder(new Order("ORD-2", 49.99, "PENDING")));
    System.out.println(describeOrder("not an order"));
  }

  // Exercise 4: Record patterns in switch expressions
  // TODO: Use record patterns inside a switch to handle different shapes.
  //
  // Records:
  //   - Circle(double radius)
  //   - Rectangle(double width, double height)
  //   - Triangle(double base, double height)
  //
  // Method: describeShape(Object obj)
  //   - Use switch(obj) with case labels:
  //       case Circle(double r)        -> "Circle with radius {r}"
  //       case Rectangle(double w, double h) -> "Rectangle {w}x{h}"
  //       case Triangle(double b, double h)  -> "Triangle with base {b} and height {h}"
  //       case null, default           -> "Unknown shape"
  //   - Return the resulting String
  //
  // The switch expression eliminates verbose if/else instanceof chains.
  // 'case null' handles null input explicitly (Java 21+).
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Record patterns in switch ===");

    System.out.println(describeShape(new Circle(5.0)));
    System.out.println(describeShape(new Rectangle(4.0, 6.0)));
    System.out.println(describeShape(new Triangle(3.0, 7.0)));
    System.out.println(describeShape(null));
    System.out.println(describeShape("not a shape"));
  }

  // =============================================
  // TODO: Implement the methods below
  // =============================================

  private static String describe(Object obj) {
    return obj instanceof Point(int x, int y) ? "Point at (" + x + ", " + y + ")" : "Not a point";
  }

  private static String describeCapital(Object obj) {
    return obj instanceof Country(String name, City(String cityName, int population))
        ? "The capital of " + name + " is " + cityName + " (pop: " + population + ")"
        : "Not a country";
  }

  private static String describeOrder(Object obj) {
    return switch (obj) {
      case Order(String id, double total, String status) when total > 100 ->
          "Premium order " + id + ": $" + total;
      case Order(String id, double total, String status) ->
          "Regular order " + id + ": $" + total;
      default -> "Not an order";
    };
  }

  private static String describeShape(Object obj) {
    return switch (obj) {
      case Circle(double r) -> "Circle with radius " + r;
      case Rectangle(double w, double h) -> "Rectangle " + w + "x" + h;
      case Triangle(double b, double h) -> "Triangle with base " + b + " and height " + h;
      case null, default -> "Unknown shape";
    };
  }

  // =============================================
  // TODO: Create your records below
  // =============================================

  // Exercise 1: Basic record
  public record Point(int x, int y) {}

  // Exercise 2: Nested records
  public record City(String name, int population) {}

  public record Country(String name, City capital) {}

  // Exercise 3: Order record
  public record Order(String id, double total, String status) {}

  // Exercise 4: Shape records
  public record Circle(double radius) {}

  public record Rectangle(double width, double height) {}

  public record Triangle(double base, double height) {}
}
