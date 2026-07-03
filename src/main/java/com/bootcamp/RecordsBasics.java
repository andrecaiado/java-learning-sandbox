package com.bootcamp;

public class RecordsBasics {

  public static void main(String[] args) {
    // exercise1();
    // exercise2();
    exercise3();
    exercise4();
  }

  // Exercise 1: Basic record creation
  // TODO: Create two records and explore their auto-generated methods.
  //
  // A) Create a record: Point(int x, int y)
  //    Rules:
  //      - Use the record keyword: public record Point(int x, int y) {}
  //      - Java auto-generates: constructor, getters, equals, hashCode, toString
  //
  // B) Create a record: Person(String name, int age)
  //    Rules:
  //      - Same as Point — just a different type
  //      - Notice how little code you write compared to a class
  public static void exercise1() {
    System.out.println("=== Exercise 1: Basic record creation ===");

    Point p1 = new Point(3, 4);
    Point p2 = new Point(3, 4);
    Point p3 = new Point(5, 6);

    // Auto-generated methods
    System.out.println("p1: " + p1); // toString
    System.out.println("x: " + p1.x()); // accessor (not getX!)
    System.out.println("y: " + p1.y()); // accessor
    System.out.println("p1 equals p2: " + p1.equals(p2)); // structural equality
    System.out.println("p1 equals p3: " + p1.equals(p3));

    Person alice = new Person("Alice", 30);
    Person bob = new Person("Bob", 25);
    System.out.println("alice: " + alice);
    System.out.println("bob: " + bob);
  }

  // Exercise 2: Validation in canonical constructor
  // TODO: Add validation to the PersonWithValidation record.
  //
  // Record: PersonWithValidation(String name, int age)
  // Rules:
  //   - Create a CANONICAL constructor (full parameter list)
  //   - Validate: name must not be null or blank
  //   - Validate: age must be between 0 and 150
  //   - Throw IllegalArgumentException for invalid inputs
  //   - Assign fields AFTER validation
  public static void exercise2() {
    System.out.println("\n=== Exercise 2: Validation in canonical constructor ===");

    try {
      PersonWithValidation valid = new PersonWithValidation("Alice", 30);
      System.out.println("Valid: " + valid);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }

    try {
      PersonWithValidation blank = new PersonWithValidation("", 30);
      System.out.println("Blank name: " + blank);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }

    try {
      PersonWithValidation badAge = new PersonWithValidation("Bob", -5);
      System.out.println("Bad age: " + badAge);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  // Exercise 3: Compact constructor
  // TODO: Use a compact constructor to normalize data.
  //
  // Record: Email(String address)
  // Rules:
  //   - Create a COMPACT constructor (no parameter list, just validation logic)
  //   - Normalize: trim whitespace and lowercase the address
  //   - Validate: must contain '@'
  //   - In compact constructors, you don't assign — fields are auto-assigned after
  public static void exercise3() {
    System.out.println("\n=== Exercise 3: Compact constructor ===");

    Email normal;
    Email invalid = null;
    try {
      normal = Email.of("  Alice@Example.COM  ");
      invalid = Email.of("not-an-email");
      System.out.println("Normal: " + normal.address()); // alice@example.com
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid present: " + e.getMessage());
    }
  }

  // Exercise 4: Records with custom methods
  // TODO: Add business logic methods to a record.
  //
  // Record: Money(double amount, String currency)
  // Rules:
  //   - Add method: boolean isPositive() — returns true if amount > 0
  //   - Add method: Money add(Money other) — adds amounts (same currency only)
  //   - Add method: Money negate() — returns new Money with negated amount
  //   - Throw IllegalArgumentException if currencies don't match in add()
  //   - Records are immutable — methods return NEW instances
  public static void exercise4() {
    System.out.println("\n=== Exercise 4: Records with custom methods ===");

    Money price = new Money(29.99, "USD");
    Money tax = new Money(2.50, "USD");
    Money discount = new Money(-5.00, "USD");
    Money euro = new Money(10.00, "EUR");

    System.out.println("price: " + price);
    System.out.println("isPositive: " + price.isPositive());
    System.out.println("negate: " + price.negate());
    System.out.println("price + tax: " + price.add(tax));
    System.out.println("price + discount: " + price.add(discount));

    try {
      Money bad = price.add(euro);
      System.out.println("Mixed currencies: " + bad);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  // =============================================
  // TODO: Create your records below
  // =============================================

  // Exercise 1: Basic records
  public record Point(int x, int y) {}

  public record Person(String name, int age) {}

  // Exercise 2: Validated record
  public record PersonWithValidation(String name, int age) {
    // Canonical constructor
    public PersonWithValidation(String name, int age) {
      if (name == null || name.isBlank())
        throw new IllegalArgumentException("Name cannot be null or blank");
      if (age < 0 || age > 150) throw new IllegalArgumentException("Age must be between 0 and 150");

      // Manually assign after validation
      this.name = name;
      this.age = age;
    }
  }

  // Exercise 3: Compact constructor
  public record Email(String address) {
    // Compact constructor
    public Email {
      if (!address.contains("@"))
        throw new IllegalArgumentException("Invalid address. Should contain '@'");
    }

    // Factory method for normalization
    public static Email of(String address) {
      return new Email(address.trim().toLowerCase());
    }
  }

  // Exercise 4: Records with methods
  public record Money(double amount, String currency) {
    public boolean isPositive() {
      return amount > 0;
    }

    public Money add(Money other) {
      if (!currency.equals(other.currency)) {
        throw new IllegalArgumentException("Currencies don't match ");
      }
      return new Money(amount + other.amount, currency);
    }

    public Money negate() {
      return new Money(amount * -1, currency);
    }
  }
}
