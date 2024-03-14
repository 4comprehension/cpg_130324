package com.for_comprehension.function.E04;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Unmodifiable {

    public static void main(String[] args) {
//        List<Integer> list = Stream.of(1, 2, null, 4).collect(Collectors.toUnmodifiableList());
        Stream.of(1, 2, null, 4)
          .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}
