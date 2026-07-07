package com.bootcamp;

public class SwitchExpressions {

  public static void main(String[] args) {
    exercise1();
    exercise2();
    exercise3();
    exercise4();
  }

  // Exercise 1: Basic switch expression with arrow syntax
  // TODO: Convert a traditional switch statement into a switch expression using arrow notation.
  //
  // Method: describeDay(int day)
  //   - day 1 -> "Monday"
  //   - day 2 -> "Tuesday"
  //   - day 3 -> "Wednesday"
  //   - day 4 -> "Thursday"
  //   - day 5 -> "Friday"
  //   - day 6, 7 -> "Weekend"
  //   - default -> "Invalid day"
  //
  // Use arrow (->) syntax — no fall-through, no break needed.
  // The switch is an expression that returns a String.
  public static void exercise1() {
    System.out.println("=== Exercise 1: Switch expression with arrow syntax ===");

    System.out.println(describeDay(1));
    System.out.println(describeDay(3));
    System.out.println(describeDay(5));
    System.out.println(describeDay(6));
    System.out.println(describeDay(9));
  }

  // Exercise 2: Pattern matching for type in switch
  // TODO: Use pattern matching to handle different types in a single switch.
  //
  // Method: describeType(Object obj)
  //   - case Integer i -> "Integer: " + i
  //   - case String s  -> "String of length " + s.length()
  //   - case int[] arr  -> "Array of " + arr.length + " elements"
  //   - case null       -> "Null input"
  //   - default         -> "Unknown type: " + obj.getClass().getSimpleName()
  //
  // Pattern matching eliminates instanceof + cast boilerplate.
  // 'case null' handles null explicitly (Java 21+).
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Pattern matching for type in switch ===");

    System.out.println(describeType(42));
    System.out.println(describeType("hello"));
    System.out.println(describeType(new int[] {1, 2, 3}));
    System.out.println(describeType(null));
    System.out.println(describeType(3.14));
  }

  // Exercise 3: Guarded patterns with when clause
  // TODO: Add 'when' guards to pattern cases for value-based filtering.
  //
  // Method: classifyNumber(int num)
  //   - case int n when n < 0  -> "Negative: " + n
  //   - case int n when n == 0 -> "Zero"
  //   - case int n when n <= 10 -> "Small positive: " + n
  //   - case int n             -> "Large positive: " + n
  //
  // The 'when' clause adds a boolean guard after the pattern.
  // Cases are checked top-to-bottom — order matters!
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Guarded patterns with when clause ===");

    System.out.println(classifyNumber(-5));
    System.out.println(classifyNumber(0));
    System.out.println(classifyNumber(7));
    System.out.println(classifyNumber(42));
  }

  // Exercise 4: Exhaustive switch with sealed-style dispatch
  // TODO: Build an exhaustive switch over a fixed set of shapes.
  //
  // Records:
  //   - Circle(double radius)
  //   - Rectangle(double width, double height)
  //   - Triangle(double base, double height)
  //
  // Method: calculateArea(Object shape)
  //   - case Circle(double r)           -> Math.PI * r * r
  //   - case Rectangle(double w, double h) -> w * h
  //   - case Triangle(double b, double h)  -> 0.5 * b * h
  //   - case null                       -> throw IllegalArgumentException("Shape cannot be null")
  //   - default                         -> throw IllegalArgumentException("Unknown shape type")
  //
  // Return the area as a double.
  // This exercise combines record patterns + switch expressions for real-world dispatch.
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Exhaustive switch with shape dispatch ===");

    System.out.println("Circle area: " + calculateArea(new Circle(5.0)));
    System.out.println("Rectangle area: " + calculateArea(new Rectangle(4.0, 6.0)));
    System.out.println("Triangle area: " + calculateArea(new Triangle(3.0, 7.0)));

    try {
      calculateArea(null);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  // =============================================
  // TODO: Implement the methods below
  // =============================================

  private static String describeDay(int day) {
    return switch (day) {
      case 1 -> "Monday";
      case 2 -> "Tuesday";
      case 3 -> "Wednesday";
      case 4 -> "Thursday";
      case 5 -> "Friday";
      case 6, 7 -> "Weekend";
      default -> "Invalid day";
    };
  }

  private static String describeType(Object obj) {
    return switch (obj) {
      case Integer i -> "Integer: " + i;
      case String s -> "String of length " + s.length();
      case int[] arr -> "Array of " + arr.length + " elements";
      case null -> "Null input";
      default -> "Unknown type: " + obj.getClass().getSimpleName();
    };
  }

  private static String classifyNumber(int num) {
    if (num < 0) {
      return "Negative: " + num;
    } else if (num == 0) {
      return "Zero";
    } else if (num <= 10) {
      return "Small positive: " + num;
    } else {
      return "Large positive: " + num;
    }
  }

  private static double calculateArea(Object shape) {
    return switch (shape) {
      case Circle(double r) -> Math.PI * r * r;
      case Rectangle(double w, double h) -> w * h;
      case Triangle(double b, double h) -> b * h * 0.5;
      case null -> throw new IllegalArgumentException("Shape cannot be null");
      default -> throw new IllegalArgumentException("Unknown shape type");
    };
  }

  // =============================================
  // TODO: Create your records below
  // =============================================

  // Exercise 4: Shape records
  public record Circle(double radius) {}

  public record Rectangle(double width, double height) {}

  public record Triangle(double base, double height) {}
}
