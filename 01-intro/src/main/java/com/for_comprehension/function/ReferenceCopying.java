package com.for_comprehension.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ReferenceCopying {

    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>(Arrays.asList(1, 2, 3));

        System.out.println("before process: " + ints);

        process(ints);

        System.out.println("after process: " + ints);
    }

    public static void process(List<Integer> ints) {
        ints = new ArrayList<>();
        System.out.println("inside process: " + ints);
    }
}
