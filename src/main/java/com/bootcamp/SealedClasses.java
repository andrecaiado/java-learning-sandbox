package com.bootcamp;

public class SealedClasses {

  public static void main(String[] args) {
    exercise1();
    exercise2();
    exercise3();
    exercise4();
  }

  // Exercise 1: Sealed interface with permits
  // TODO: Define a sealed interface that restricts which classes can implement it.
  //
  // Sealed interface: Shape
  //   permits Circle, Rectangle, Triangle
  //
  // Records:
  //   - Circle(double radius)              — final
  //   - Rectangle(double width, double height) — final
  //   - Triangle(double base, double height)   — final
  //
  // Method: area(Shape shape)
  //   - case Circle(double r)     -> Math.PI * r * r
  //   - case Rectangle(double w, double h) -> w * h
  //   - case Triangle(double b, double h)  -> 0.5 * b * h
  //   - case null -> throw IllegalArgumentException("Shape cannot be null")
  //
  // The compiler knows ALL possible subtypes, so no default case is needed.
  // This is exhaustive — adding a new Shape subtype without updating the switch is a compile error.
  public static void exercise1() {
    System.out.println("=== Exercise 1: Sealed interface with permits ===");

    System.out.println("Circle area: " + area(new Circle(5.0)));
    System.out.println("Rectangle area: " + area(new Rectangle(4.0, 6.0)));
    System.out.println("Triangle area: " + area(new Triangle(3.0, 7.0)));
  }

  // Exercise 2: Sealed class hierarchy with mixed permitted subtypes
  // TODO: Define a sealed class with a mix of final, non-sealed, and sealed subtypes.
  //
  // Sealed class: Beverage
  //   permits Coffee, Tea, Juice
  //
  // Final subtypes:
  //   - Coffee(String roast, boolean hasMilk)  — final
  //   - Juice(String fruit)                   — final
  //
  // Non-sealed subtype:
  //   - Tea(String variety)                   — non-sealed (allows extension)
  //
  // Method: describe(Beverage b)
  //   - case Coffee(String roast, boolean milk) when milk
  //       -> "Coffee with " + roast + " roast and milk"
  //   - case Coffee(String roast, boolean milk)
  //       -> "Black " + roast + " coffee"
  //   - case Tea(String variety)
  //       -> "Tea: " + variety
  //   - case Juice(String fruit)
  //       -> "Fresh " + fruit + " juice"
  //   - case null -> "No beverage"
  //
  // Using 'non-sealed' opens ONE branch for extension while keeping the rest closed.
  // This models real-world hierarchies where some subtypes need extensibility.
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Sealed class with mixed subtypes ===");

    System.out.println(describe(new Coffee("dark", true)));
    System.out.println(describe(new Coffee("light", false)));
    System.out.println(describe(new Tea("green")));
    System.out.println(describe(new Juice("orange")));
    System.out.println(describe(null));
  }

  // Exercise 3: Exhaustive switch over sealed hierarchy
  // TODO: Build a sealed hierarchy for command processing with exhaustive dispatch.
  //
  // Sealed interface: Command
  //   permits LoginCommand, LogoutCommand, SendMessageCommand
  //
  // Records:
  //   - LoginCommand(String username)          — final
  //   - LogoutCommand(String username)         — final
  //   - SendMessageCommand(String from, String to, String body) — final
  //
  // Method: execute(Command cmd)
  //   - case LoginCommand(String user)
  //       -> "User '" + user + "' logged in"
  //   - case LogoutCommand(String user)
  //       -> "User '" + user + "' logged out"
  //   - case SendMessageCommand(String from, String to, String body)
  //       -> from + " -> " + to + ": " + body
  //   - case null -> "Null command"
  //
  // No default needed — compiler enforces exhaustiveness.
  // If you add a new Command subtype, the switch WILL NOT compile until you handle it.
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Exhaustive switch over commands ===");

    System.out.println(execute(new LoginCommand("alice")));
    System.out.println(execute(new SendMessageCommand("alice", "bob", "Hello!")));
    System.out.println(execute(new LogoutCommand("alice")));
    System.out.println(execute(null));
  }

  // Exercise 4: Sealed hierarchy for mathematical expressions
  // TODO: Model an expression tree using sealed classes.
  //
  // Sealed interface: Expr
  //   permits Number, Add, Multiply, Negate
  //
  // Records/Classes:
  //   - Number(double value)                   — final record
  //   - Add(Expr left, Expr right)             — final record (nested sealed types)
  //   - Multiply(Expr left, Expr right)        — final record (nested sealed types)
  //   - Negate(Expr operand)                   — final record (nested sealed types)
  //
  // Method: evaluate(Expr expr)
  //   - case Number(double v)          -> v
  //   - case Add(Expr l, Expr r)       -> evaluate(l) + evaluate(r)
  //   - case Multiply(Expr l, Expr r)  -> evaluate(l) * evaluate(r)
  //   - case Negate(Expr e)            -> -evaluate(e)
  //   - case null -> throw IllegalArgumentException("Null expression")
  //
  // This demonstrates recursive sealed types — Expr references itself through its subtypes.
  // Pattern matching + sealed types = type-safe expression trees without visitors.
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Expression tree with sealed hierarchy ===");

    // (3 + 5) * 2
    Expr expr1 = new Multiply(new Add(new Number(3), new Number(5)), new Number(2));
    System.out.println("(3 + 5) * 2 = " + evaluate(expr1));

    // -(4 + 1)
    Expr expr2 = new Negate(new Add(new Number(4), new Number(1)));
    System.out.println("-(4 + 1) = " + evaluate(expr2));

    // Just a number
    System.out.println("42 = " + evaluate(new Number(42)));
  }

  // =============================================
  // TODO: Implement the methods below
  // =============================================

  private static double area(Shape shape) {
    return switch (shape) {
      case Circle(double r) -> Math.PI * r * r;
      case Rectangle(double w, double h) -> w * h;
      case Triangle(double b, double h) -> b * h * 0.5;
      case null -> throw new IllegalArgumentException("Shape cannot be null");
    };
  }

  private static String describe(Beverage b) {
    return switch (b) {
      case Coffee c when c.hasMilk() -> "Coffee with " + c.roast() + " roast and milk";
      case Coffee c -> "Black " + c.roast() + " coffee";
      case Tea t -> "Tea: " + t.variety();
      case Juice j -> "Fresh " + j.fruit() + " juice";
      case null -> "No beverage";
      default -> "Unknown beverage";
    };
  }

  private static String execute(Command cmd) {
    return switch (cmd) {
      case LoginCommand c -> "User '" + c.username() + "' logged in";
      case LogoutCommand c -> "User '" + c.username() + "' logged out";
      case SendMessageCommand c -> c.from() + " -> " + c.to() + ": " + c.body();
      case null -> "Null command";
    };
  }

  private static double evaluate(Expr expr) {
    return switch (expr) {
      case Number n -> n.value();
      case Add e -> evaluate(e.left()) + evaluate(e.right());
      case Multiply e -> evaluate(e.left()) * evaluate(e.right());
      case Negate e -> -evaluate(e.operand());
      case null -> throw new IllegalArgumentException("Null expression");
    };
  }

  // =============================================
  // Sealed hierarchies (defined for compilation)
  // =============================================

  // Exercise 1: Shape hierarchy
  public sealed interface Shape permits Circle, Rectangle, Triangle {}

  public record Circle(double radius) implements Shape {}

  public record Rectangle(double width, double height) implements Shape {}

  public record Triangle(double base, double height) implements Shape {}

  // Exercise 2: Beverage hierarchy
  public static sealed class Beverage permits Coffee, Tea, Juice {}

  public static final class Coffee extends Beverage {
    private final String roast;
    private final boolean hasMilk;

    public Coffee(String roast, boolean hasMilk) {
      this.roast = roast;
      this.hasMilk = hasMilk;
    }

    public String roast() {
      return roast;
    }

    public boolean hasMilk() {
      return hasMilk;
    }
  }

  public static non-sealed class Tea extends Beverage {
    private final String variety;

    public Tea(String variety) {
      this.variety = variety;
    }

    public String variety() {
      return variety;
    }
  }

  public static final class Juice extends Beverage {
    private final String fruit;

    public Juice(String fruit) {
      this.fruit = fruit;
    }

    public String fruit() {
      return fruit;
    }
  }

  // Exercise 3: Command hierarchy
  public sealed interface Command permits LoginCommand, LogoutCommand, SendMessageCommand {}

  public record LoginCommand(String username) implements Command {}

  public record LogoutCommand(String username) implements Command {}

  public record SendMessageCommand(String from, String to, String body) implements Command {}

  // Exercise 4: Expression hierarchy
  public sealed interface Expr permits Number, Add, Multiply, Negate {}

  public record Number(double value) implements Expr {}

  public record Add(Expr left, Expr right) implements Expr {}

  public record Multiply(Expr left, Expr right) implements Expr {}

  public record Negate(Expr operand) implements Expr {}
}
