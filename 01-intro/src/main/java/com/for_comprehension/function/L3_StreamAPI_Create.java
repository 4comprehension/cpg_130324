package com.for_comprehension.function;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class L3_StreamAPI_Create {

    public static void main(String[] args) {
        // create

        Stream<Integer> s1 = Stream.of(1, 2, 3);
        List<Integer> ints = List.of(1, 2, 3);
        Stream<Integer> s2 = ints.stream();

        Stream<Object> s3 = Stream.builder()
          .add(1)
          .add(2)
          .add(3)
          .build();

        Stream<Integer> s4 = Stream.generate(() -> 42);
        Stream<Integer> s5 = Stream.iterate(0, i -> i + 1);
        Stream<Integer> s6 = Stream.concat(s1, s2);

        IntStream s7 = IntStream.range(0, 1000);
        LongStream s8 = LongStream.rangeClosed(0, 1000);

        StreamSupport.stream(ints.spliterator(), false);
    }
}
