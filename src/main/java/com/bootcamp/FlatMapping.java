package com.bootcamp;

import static java.util.stream.Collectors.toList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Exercise: FlatMapping Complex Nested Data Topologies
 *
 * <p>Implement the methods below using: - Stream.flatMap() to flatten nested collections -
 * Optional.flatMap() for chaining nullable transformations - flatMap + filter/distinct for
 * deduplication across nested structures - flatMap combined with downstream collectors - Deeply
 * nested flattening (3+ levels)
 */
public class FlatMapping {

  // TODO 1: Flatten a list of lists into a single list.
  //         Use flatMap() to unwrap each inner list.
  //         Example: [[1,2], [3], [4,5,6]] -> [1, 2, 3, 4, 5, 6]
  public static List<Integer> flatten(List<List<Integer>> nested) {
    return nested.stream().flatMap(Collection::stream).collect(toList());
  }

  // TODO 2: Flatten nested lists and keep only non-empty strings.
  //         Use flatMap() with filter().
  //         Example: [["", "Hi"], [], ["Hello", ""]] -> ["Hi", "Hello"]
  public static List<String> flattenNonEmpty(List<List<String>> nested) {
    return nested.stream().flatMap(Collection::stream).filter(n -> !n.isEmpty()).collect(toList());
  }

  // TODO 3: Compute the Cartesian product of two lists.
  //         For each element in listA, pair it with every element in listB.
  //         Use flatMap to expand each a into a stream of pairs.
  //         Example: ["X","Y"], [1,2] -> [(X,1), (X,2), (Y,1), (Y,2)]
  //         Return a List<String[]> where each array is [a, b].
  public static List<String[]> cartesianProduct(List<String> listA, List<Integer> listB) {
    return listA.stream()
        .flatMap(a -> listB.stream().map(b -> new String[] {a, String.valueOf(b)}))
        .collect(toList());
  }

  // TODO 4: Use Optional.flatMap() to safely chain two lookups.
  //         Given a Map<Integer, String> and a Map<String, Integer>,
  //         look up the name by id, then look up the score by name.
  //         Return Optional<Integer> (empty if any step is missing).
  //         Example: id=1, names={1->"Alice"}, scores={"Alice"->95} -> Optional[95]
  public static Optional<Integer> chainLookup(
      int id, Map<Integer, String> names, Map<String, Integer> scores) {
    return Optional.ofNullable(names.get(id)).flatMap(n -> Optional.ofNullable(scores.get(n)));
  }

  // TODO 5: Split each sentence into individual words, then flatten into one list.
  //         Use flatMap() to split each string by space.
  //         Example: ["Hello World", "Java Streams"] -> ["Hello", "World", "Java", "Streams"]
  public static List<String> splitToWords(List<String> sentences) {
    return sentences.stream().flatMap(n -> Arrays.stream(n.split(" "))).collect(toList());
  }

  // TODO 6: Flatten a nested domain model.
  //         Given a List<Department>, each containing a List<Employee> (use records below),
  //         return a flat list of all employees.
  //         Use flatMap to extract each department's employee stream.
  public static List<Employee> flattenDepartments(List<Department> departments) {
    return departments.stream().flatMap(d -> d.employees.stream()).collect(toList());
  }

  public record Employee(String name, String role) {}

  public record Department(String name, List<Employee> employees) {}

  // TODO 7: Collect all distinct first characters across nested lists of words.
  //         Flatten the nested lists, then extract the first character of each word,
  //         and collect distinct characters into a Set<Character>.
  //         Example: [["Apple","Banana"], ["Avocado"]] -> {'A', 'B'}
  public static Set<Character> distinctFirstChars(List<List<String>> nestedWords) {
    return nestedWords.stream().flatMap(wl -> wl.stream().flatMap(w -> Stream.of(w.charAt(0)))).collect(Collectors.toSet());
  }

  // TODO 8: Flatten nested integer lists and compute the sum of all elements.
  //         Use flatMapToInt() to avoid boxing overhead, then sum().
  //         Example: [[1,2], [3,4], [5]] -> 15
  public static int flattenAndSum(List<List<Integer>> nested) {
    return nested.stream().flatMapToInt(l -> l.stream().flatMapToInt(IntStream::of)).sum();
  }

  // TODO 9: Flatten nested lists, then group elements by whether they are even or odd.
  //         Return a Map<String, List<Integer>> where key is "even" or "odd".
  //         Example: [[1,2], [3,4]] -> {odd=[1,3], even=[2,4]}
  public static Map<String, List<Integer>> flattenThenGroupByParity(List<List<Integer>> nested) {
    return nested.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.groupingBy(i -> i % 2 == 0 ? "even" : "odd"));
  }

  // TODO 10: Flatten three levels of nesting into a single list.
  //          Given List<List<List<Integer>>>, produce a flat List<Integer>.
  //          Use two chained flatMap() calls.
  //          Example: [[[1,2],[3]],[[4,5]]] -> [1, 2, 3, 4, 5]
  public static List<Integer> flattenDeep(List<List<List<Integer>>> deepNested) {
    return deepNested.stream().flatMap( l1 -> l1.stream().flatMap( Collection::stream)).collect(toList());
  }

  public static void main(String[] args) {
    System.out.println("=== FlatMapping Complex Nested Data Topologies ===");
    System.out.println();

    List<List<Integer>> nestedNums = List.of(List.of(1, 2), List.of(3), List.of(4, 5, 6));
    System.out.println("TODO 1 - Flatten:");
    System.out.println(flatten(nestedNums));
    System.out.println();

    List<List<String>> nestedStrings = List.of(List.of("", "Hi"), List.of(), List.of("Hello", ""));
    System.out.println("TODO 2 - Flatten Non Empty:");
    System.out.println(flattenNonEmpty(nestedStrings));
    System.out.println();

    System.out.println("TODO 3 - Cartesian Product:");
    List<String[]> product = cartesianProduct(List.of("X", "Y"), List.of(1, 2));
    product.forEach(p -> System.out.println("(" + p[0] + ", " + p[1] + ")"));
    System.out.println();

    Map<Integer, String> names = Map.of(1, "Alice", 2, "Bob");
    Map<String, Integer> scores = Map.of("Alice", 95, "Charlie", 88);
    System.out.println("TODO 4 - Chain Lookup (id=1):");
    System.out.println(chainLookup(1, names, scores));
    System.out.println("TODO 4 - Chain Lookup (id=99):");
    System.out.println(chainLookup(99, names, scores));
    System.out.println();

    List<String> sentences = List.of("Hello World", "Java Streams");
    System.out.println("TODO 5 - Split To Words:");
    System.out.println(splitToWords(sentences));
    System.out.println();

    List<Department> departments =
        List.of(
            new Department(
                "Engineering", List.of(new Employee("Alice", "Dev"), new Employee("Bob", "QA"))),
            new Department("Sales", List.of(new Employee("Charlie", "Rep"))));
    System.out.println("TODO 6 - Flatten Departments:");
    System.out.println(flattenDepartments(departments));
    System.out.println();

    List<List<String>> nestedWords =
        List.of(List.of("Apple", "Banana"), List.of("Avocado"), List.of("Blueberry"));
    System.out.println("TODO 7 - Distinct First Chars:");
    System.out.println(distinctFirstChars(nestedWords));
    System.out.println();

    System.out.println("TODO 8 - Flatten And Sum:");
    System.out.println(flattenAndSum(nestedNums));
    System.out.println();

    System.out.println("TODO 9 - Flatten Then Group By Parity:");
    System.out.println(flattenThenGroupByParity(nestedNums));
    System.out.println();

    List<List<List<Integer>>> deepNested =
        List.of(List.of(List.of(1, 2), List.of(3)), List.of(List.of(4, 5)));
    System.out.println("TODO 10 - Flatten Deep:");
    System.out.println(flattenDeep(deepNested));
  }
}
