package com.for_comprehension.function;

import java.util.stream.Stream;

class L3_PrimitiveStreams {

    public static void main(String[] args) {
        Stream.of(1, 2, 3, null, 5)
          .mapToInt(i -> i == null ? 0 : i)
          .map(i -> i)
          .boxed()
          .forEach(System.out::println);
    }
}
