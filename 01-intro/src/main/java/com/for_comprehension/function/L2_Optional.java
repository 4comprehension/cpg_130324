package com.for_comprehension.function;

import java.util.Optional;

class L2_Optional {

    public static void main(String[] args) {
        // create
        Optional<String> o1 = Optional.ofNullable("");
        Optional<String> o2 = Optional.of("");

        // process
        Optional<String> o3 = findUserById(1).map(i -> i.toUpperCase());
        Optional<String> o4 = findUserById(1).flatMap(i -> findUserById(1));

        Optional<String> o5 = findUserById(1).filter(i -> !i.isBlank());
        Optional<String> o6 = findUserById(1).or(() -> findUserById(2));

        // extract
        String r1 = findUserById(1).orElse("default");
        String r2 = findUserById(1).orElseGet(() -> "result of some expensive operation");

        String r3 = findUserById(1).orElseThrow();
        String r4 = findUserById(1).orElseThrow(IllegalStateException::new);

        boolean r5 = findUserById(1).isPresent();
        boolean r6 = findUserById(1).isEmpty();

        findUserById(1).ifPresent(System.out::println);
        findUserById(1).ifPresentOrElse(System.out::println, () -> System.out.println(":("));
    }

    public static Optional<String> findUserById(int id) {
        return id % 3 == 0
          ? Optional.of("Adam")
          : Optional.empty();
    }
}
