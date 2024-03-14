package com.for_comprehension.function;

import java.util.stream.Stream;

class L3_StreamOnceOff {

    public static void main(String[] args) {
        Stream<Integer> s = Stream.of(1, 2, 3);
        s.forEach(System.out::println);

        Stream<Integer> s2 = Stream.of(1, 2, 3);
        s2.forEach(System.out::println);
    }
}
