package com.for_comprehension.function;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class L3_StreamAPI_Terminal {

    public static void main(String[] args) {
        var ints = List.of(1, 2, 3, 4);

        Optional<Integer> r1 = ints.stream().findAny();
        Optional<Integer> r2 = ints.stream().findFirst();

        List<Integer> l1 = ints.stream().collect(Collectors.toList());
        List<Integer> l2 = ints.stream().toList();

        long c1 = ints.stream().count();

        Optional<Integer> max = ints.stream().max(Comparator.naturalOrder());
        Optional<Integer> min = ints.stream().min(Comparator.naturalOrder());

        boolean allMatch = ints.stream().allMatch(i -> i > 0);
        boolean anyMatch = ints.stream().anyMatch(i -> i > 0);
        boolean noneMatch = ints.stream().noneMatch(i -> i > 0);

        Optional<Integer> r3 = ints.stream().reduce((i1, i2) -> i1 + i2);
        Integer r4 = ints.stream().reduce(0, (i1, i2) -> i1 + i2);
    }
}
