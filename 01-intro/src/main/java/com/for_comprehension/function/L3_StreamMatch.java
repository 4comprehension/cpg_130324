package com.for_comprehension.function;

import java.util.stream.Stream;

class L3_StreamMatch {

    public static void main(String[] args) {
        boolean r1 = Stream.<Integer>of().allMatch(i -> i > 0);
        System.out.println(r1);

        boolean r2 = Stream.<Integer>of().noneMatch(i -> i > 0);
        System.out.println(r2);

        boolean r3 = Stream.<Integer>of().anyMatch(i -> i > 0);
        System.out.println(r3);
    }
}
