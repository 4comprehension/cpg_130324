package com.for_comprehension.function;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class L3_NullsAgainstCollectors {

    // https://4comprehension.com/nulls-against-collectors/
    public static void main(String[] args) {
        var strings = Arrays.asList("a", "bb", "ccc", null, "dd");

        List<String> l1 = strings.stream().toList();
        System.out.println(l1);

//        List<String> l0 = strings.stream().toList();
//        List<String> l1 = strings.stream().collect(Collectors.toList());
//        List<String> l2 = strings.stream().collect(Collectors.toUnmodifiableList());
    }
}
