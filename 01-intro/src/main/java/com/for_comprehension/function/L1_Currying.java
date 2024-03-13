package com.for_comprehension.function;

import java.util.function.BiFunction;
import java.util.function.Function;

class L1_Currying {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> bf1 = (i1, i2) -> i1 + i2;
        L1_Function.TriFunction<Integer,Integer,Integer, Integer> tf1 = (i1,i2,i3) -> i1 + i2 + i3;

        Function<Integer, Function<Integer, Integer>> bf1_curried = i1 -> i2 -> i1 + i2;
        Integer r = bf1.apply(1, 2);
        Integer r2 = bf1_curried.apply(1).apply(2);

        Function<Integer, Function<Integer, Function<Integer, Integer>>> tf1_curried = i1 -> i2 -> i3 -> i1 + i2 + i3;
    }
}
