package com.for_comprehension.function.E02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

class Optionals {

    private static final Logger log = LoggerFactory.getLogger(Optionals.class);

    private static final String DEFAULT = "DEFAULT";

    /**
     * Call {@link this#findOneById } with a provided id and handle absence by throwing {@link NoSuchElementException}
     * Hint: {@link Optional#get()}
     * <p>
     * WARNING: this is an anti-pattern (unless you know what you're doing)
     */
    static Function<Integer, Person> L1_bruteForceGet() {
        return i -> findOneById(i).orElseThrow();
    }

    /**
     * Call {@link this#findOneById } with a provided id and handle absence by throwing {@link IllegalStateException}
     * Hint: check {@link Optional}'s API
     */
    static Function<Integer, Person> L2_customException() {
        return i -> findOneById(i).orElseThrow(IllegalStateException::new);
    }

    /**
     * Get {@link Person#name} if found or else return {@link this#DEFAULT}
     */
    static Function<Integer, String> L3_defaultValue() {
        return i -> findOneById(i)
          .map(Person::getName)
          .orElse(DEFAULT);
    }

    /**
     * Get {@link Person#name} if found or else return the value returned by provided method (represented by Supplier<String)
     */
    static BiFunction<Integer, Supplier<String>, String> L4_defaultValueMethodResult() {
        return (i, supplier) -> findOneById(i)
          .map(Person::getName)
          .orElseGet(supplier);
    }

    /**
     * Get {@link Person#name}, uppercase it,
     * if empty string, treat as absent
     * or else return a default value
     * Hint: {@link Optional#filter}
     */
    static Function<Integer, String> L5_processValue() {
        return i -> findOneById(i)
          .map(p -> p.getName())
          .filter(name -> !name.isBlank())
          .map(name -> name.toUpperCase())
          .orElse(DEFAULT);
    }

    /**
     * Get {@link Person#name}
     * if empty string, treat as absent
     * or else return a default value (42)
     * and then find age by calling {@link this#findAgeByName(String)}
     * Hint: {@link Optional#flatMap(Function)}
     */
    static Function<Integer, Integer> L6_nestedOptionals() {
        return i -> findOneById(i)
          .map(Person::getName)
          .filter(name -> !name.isBlank())
          .flatMap(Optionals::findAgeByName)
          .orElse(42);
    }

    static class Person {

        static final Person ANDRE = new Person("Andre");

        String name;

        Person(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    private static Optional<Integer> findAgeByName(String name) {
        return Optional.of(name.hashCode() % 80);
    }

    private static Optional<Person> findOneById(int id) {
        log.info("Fetching person for id [{}]", id);
        return id == 42 ? Optional.of(new Person("Andre")) : Optional.empty();
    }

    public static <T> T todo() {
        throw new RuntimeException("implement me");
    }
}