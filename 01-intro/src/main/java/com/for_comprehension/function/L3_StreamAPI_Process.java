package com.for_comprehension.function;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

class L3_StreamAPI_Process {

    public static void main(String[] args) {
        var ints = List.of(1, 2, 3, 4);

        Stream.of(1, 2, null, 4)
          .filter(Objects::nonNull)
          .forEach(System.out::println);

        System.out.println("----");

        ints.stream()
          .map(i -> i * 2)
          .filter(i -> i > 4)
          .forEach(System.out::println);

        List.of("aaa", "bb", "dddd", "e").stream()
          .sorted(comparing(String::length)
            .thenComparing(Comparator.naturalOrder()))
          .forEach(System.out::println);

        List.of("12", "2", "5", "1", "10").stream()
          .sorted(Comparator.comparingInt(Integer::parseInt))
          .forEach(System.out::println);

        List.of("aaa", "bb", "bb", "BB", "cccc", "dddd", "e").stream()
          .distinct()
          .forEach(System.out::println);

        List.of("aaa", "bb", "bb", "BB", "cccc", "dddd", "e").stream()
          .skip(2)
          .limit(10);

        Stream.iterate(0, i -> i + 1)
          .dropWhile(i -> i < 10)
          .takeWhile(i -> i < 20)
          .forEach(System.out::println);

        ints.stream()
          .mapToInt(i -> i * 2)
          .forEach(System.out::println);
    }
}
