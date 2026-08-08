package com.bootcamp;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Exercise: Text Blocks & Unnamed Variables (Java 21+)
 *
 * <p>Implement the methods below using: - Text Blocks (triple-quoted multi-line strings with """) -
 * Unnamed variables (_ in lambdas, catch blocks, for-loops, try-with-resources) - Text Block
 * formatting: indent trimming, \s, \n, \
 */
public class TextBlocksAndUnnamedVars {

  // TODO 1: Return a formatted SQL query using a Text Block.
  //         Ensure proper indentation is trimmed and the result matches
  //         "SELECT u.name, u.email\nFROM users u\nWHERE u.active = true\nORDER BY u.name"
  public static String buildSqlQuery() {
    return """
    SELECT u.name, u.email
    FROM users u
    WHERE u.active = true
    ORDER BY u.name\
    """;
  }

  // TODO 2: Return a JSON string using a Text Block with embedded values.
  //         Use the name and age parameters to produce valid JSON:
  //         {\n  "name": "<name>",\n  "age": <age>\n}
  public static String buildJson(String name, int age) {
    return """
    {
      "name": %s,
      "age": %d
    }
    """
        .formatted(name, age);
  }

  // TODO 3: Demonstrate Text Block escape sequences.
  //         Return a string that literally contains a double-quote character
  //         using the \" escape inside a text block (not concatenated).
  public static String withEscapedQuote() {
    return """
    \"Hello\"
    """;
  }

  // TODO 4: Use an unnamed variable (_) in a lambda that takes two parameters
  //         but only uses the first. Return the result of applying the lambda
  //         to the given values.
  public static String unnamedLambdaParam(String value, int ignored) {
    BiFunction<String, Integer, String> lambda = (String s, Integer _) -> s;
    return lambda.apply(value, ignored);
  }

  // TODO 5: Use unnamed variables in a for-loop that iterates over a list
  //         but only cares about the index. Return a comma-separated string
  //         of indices where the corresponding element starts with 'A'.
  //         Example: ["Apple", "Banana", "Avocado"] -> "0,2"
  public static String findIndicesStartingWithA(List<String> items) {
    StringBuilder idxs = new StringBuilder();
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).startsWith("A")) {
        if (!idxs.isEmpty()) {
          idxs.append(",");
        }
        idxs.append(i);
      }
    }
    return idxs.toString();
  }

  // TODO 6: Use an unnamed variable in a catch block where the exception
  //         is intentionally swallowed. Return "OK" if no exception occurs,
  //         or "CAUGHT" if the provided Runnable throws.
  public static String swallowException(Runnable action) {
    try {
      action.run();
      return "OK";
    } catch (Exception _) {
      return "CAUGHT";
    }
  }

  // TODO 7: Combine Text Blocks with String.formatted() to create a
  //         multi-line template. Return a string like:
  //         "Report for: <department>\n================\nTotal employees: <count>\nAvg salary:
  // <salary>"
  public static String buildReport(String department, int count, double avgSalary) {
    return """
    Report for: %s
    ================
    Total employees: %d
    Avg salary: %.2f
    """
        .formatted(department, count, avgSalary);
  }

  public static void main(String[] args) {
    System.out.println("=== Text Blocks & Unnamed Variables ===");
    System.out.println();

    System.out.println("TODO 1 - SQL Query:");
    System.out.println(buildSqlQuery());
    System.out.println();

    System.out.println("TODO 2 - JSON:");
    System.out.println(buildJson("Alice", 30));
    System.out.println();

    System.out.println("TODO 3 - Escaped Quote:");
    System.out.println(withEscapedQuote());
    System.out.println();

    System.out.println("TODO 4 - Unnamed Lambda:");
    System.out.println(unnamedLambdaParam("hello", 42));
    System.out.println();

    System.out.println("TODO 5 - Indices Starting with A:");
    System.out.println(
        findIndicesStartingWithA(java.util.List.of("Apple", "Banana", "Avocado", "Cherry")));
    System.out.println();

    System.out.println("TODO 6 - Swallow Exception:");
    System.out.println(
        swallowException(
            () -> {
              throw new RuntimeException("fail");
            }));
    System.out.println();

    System.out.println("TODO 7 - Report:");
    System.out.println(buildReport("Engineering", 25, 95000.50));
  }
}
