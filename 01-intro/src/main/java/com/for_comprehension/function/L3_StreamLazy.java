package com.for_comprehension.function;

import java.util.stream.Stream;

class L3_StreamLazy {

    public static void main(String[] args) {
        Stream.of(1, 2, 3).map(i -> {
            System.out.println(i);
            return i;
        });
    }
}
