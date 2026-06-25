package com.bootcamp;

import java.util.Optional;

public class OptionalBasics {

    public static void main(String[] args) {
        exercise1();
        exercise2();
        exercise3();
        exercise4();
    }

    // Exercise 1: Creating & unwrapping Optional
    // TODO: Implement three methods that demonstrate Optional creation and safe unwrapping.
    //
    // A) safeLookup:
    //    Signature hint: public static Optional<String> safeLookup(String[] array, int index)
    //    Rules:
    //      - Return Optional.of(array[index]) if index is valid
    //      - Return Optional.empty() if index is out of bounds or array is null
    //      - Do NOT throw exceptions
    //
    // B) unwrapWithDefault:
    //    Signature hint: public static String unwrapWithDefault(Optional<String> opt, String defaultValue)
    //    Rules:
    //      - Return opt.orElse(defaultValue)
    //      - This is the simplest way to handle missing values
    //
    // C) unwrapOrCompute:
    //    Signature hint: public static String unwrapOrCompute(Optional<String> opt)
    //    Rules:
    //      - Return opt.orElseGet(() -> "computed default")
    //      - The lambda should only execute when the Optional is empty
    public static void exercise1() {
        System.out.println("=== Exercise 1: Creating & unwrapping Optional ===");

        String[] names = {"Alice", "Bob", "Charlie"};
        Optional<String> found = safeLookup(names, 1);
        Optional<String> missing = safeLookup(names, 10);
        Optional<String> nullArray = safeLookup(null, 0);

        System.out.println("Found: " + found.orElse("empty"));
        System.out.println("Missing: " + missing.orElse("empty"));
        System.out.println("Null array: " + nullArray.orElse("empty"));

        Optional<String> present = Optional.of("hello");
        Optional<String> absent = Optional.empty();

        System.out.println("Present with default: " + unwrapWithDefault(present, "default"));
        System.out.println("Absent with default: " + unwrapWithDefault(absent, "default"));

        System.out.println("Present computed: " + unwrapOrCompute(present));
        System.out.println("Absent computed: " + unwrapOrCompute(absent));
    }

    // Exercise 2: Transforming with map and flatMap
    // TODO: Implement two methods that transform Optional values.
    //
    // A) doubleLength:
    //    Signature hint: public static Optional<Integer> doubleLength(Optional<String> opt)
    //    Rules:
    //      - Use opt.map(s -> s.length() * 2)
    //      - Return empty if opt is empty
    //      - Do NOT use isPresent() — let map handle it
    //
    // B) findUserEmail:
    //    Signature hint: public static Optional<String> findUserEmail(Optional<User> optUser)
    //    Rules:
    //      - Use optUser.map(User::email)
    //      - Return empty if user is empty
    //      - This demonstrates method reference inside map
    public static void exercise2() {
        System.out.println("\n=== Exercise 2: Transforming with map and flatMap ===");

        Optional<String> hello = Optional.of("hello");
        Optional<String> empty = Optional.empty();

        System.out.println("Double length of 'hello': " + doubleLength(hello));
        System.out.println("Double length of empty: " + doubleLength(empty));

        Optional<User> user = Optional.of(new User("alice@example.com"));
        Optional<User> noUser = Optional.empty();

        System.out.println("User email: " + findUserEmail(user));
        System.out.println("No user email: " + findUserEmail(noUser));
    }

    // Exercise 3: Filtering with predicates
    // TODO: Implement a method that filters Optional values.
    //
    // Signature hint: public static Optional<String> filterEvenLength(Optional<String> opt)
    // Rules:
    //   - Use opt.filter(s -> s.length() % 2 == 0)
    //   - Return the Optional only if the string length is even
    //   - Return empty if length is odd or Optional is empty
    //   - Do NOT use isPresent() or ifPresent() — let filter handle it
    public static void exercise3() {
        System.out.println("\n=== Exercise 3: Filtering with predicates ===");

        Optional<String> even = Optional.of("java");    // length 4
        Optional<String> odd = Optional.of("hello");     // length 5

        System.out.println("Filter 'java' (len 4): " + filterEvenLength(even));
        System.out.println("Filter 'hello' (len 5): " + filterEvenLength(odd));
        System.out.println("Filter empty: " + filterEvenLength(Optional.empty()));
    }

    // Exercise 4: Real pipeline — replacing null-heavy code
    // TODO: Rewrite the null-heavy getDiscountDescription method using Optional.
    //
    // Signature hint: public static String getDiscountDescription(Optional<Order> optOrder)
    // Rules:
    //   - Chain: optOrder.map(Order::discountPercent).filter(d -> d > 0).map(d -> d + "% off")
    //   - Return the discount string if present, otherwise "No discount"
    //   - Use orElse("No discount") at the end
    //   - This replaces 3-4 nested if-null checks with one clean pipeline
    public static void exercise4() {
        System.out.println("\n=== Exercise 4: Real pipeline ===");

        Optional<Order> withDiscount = Optional.of(new Order(15));
        Optional<Order> noDiscount = Optional.of(new Order(0));
        Optional<Order> emptyOrder = Optional.empty();

        System.out.println("With discount: " + getDiscountDescription(withDiscount));
        System.out.println("No discount: " + getDiscountDescription(noDiscount));
        System.out.println("Empty order: " + getDiscountDescription(emptyOrder));
    }

    // =============================================
    // TODO: Create your generic class and methods below
    // =============================================

    // Exercise 1 methods
    public static Optional<String> safeLookup(String[] array, int index) {
        return array != null && index >= 0 && index < array.length ? Optional.of(array[index]) : Optional.empty();
    }

    public static String unwrapWithDefault(Optional<String> opt, String defaultValue) {
        return opt.orElse(defaultValue);
    }

    public static String unwrapOrCompute(Optional<String> opt) {
        return opt.orElseGet(() -> "computed default");
    }

    // Exercise 2 methods
    public static Optional<Integer> doubleLength(Optional<String> opt) {
        return opt.map(s -> s.length() * 2);
    }

    public static Optional<String> findUserEmail(Optional<User> optUser) {
        return optUser.map(User::email);
    }

    // Exercise 3 method
    public static Optional<String> filterEvenLength(Optional<String> opt) {
        return opt.filter( s -> s.length() % 2 == 0);
    }

    // Exercise 4 method
    public static String getDiscountDescription(Optional<Order> optOrder) {
        return optOrder.map(Order::discountPercent).filter(d -> d > 0).map(d -> d + "% off").orElse("No discount");
    }

    // =============================================
    // Helper classes for exercises
    // =============================================

    static class User {
        private final String email;

        User(String email) {
            this.email = email;
        }

        public String email() {
            return email;
        }

        @Override
        public String toString() {
            return "User{" + email + "}";
        }
    }

    static class Order {
        private final int discountPercent;

        Order(int discountPercent) {
            this.discountPercent = discountPercent;
        }

        public int discountPercent() {
            return discountPercent;
        }

        @Override
        public String toString() {
            return "Order{discount=" + discountPercent + "%}";
        }
    }
}
