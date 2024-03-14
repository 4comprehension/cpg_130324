package com.for_comprehension.function;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;

class L3_StreamCollectors {

    public static void main(String[] args) {
        var strings = List.of("a", "bb", "ccc", "dd");

        List<String> l0 = strings.stream().toList();
        List<String> l1 = strings.stream().collect(Collectors.toList());
        List<String> l2 = strings.stream().collect(Collectors.toUnmodifiableList());
        LinkedList<String> l3 = strings.stream().collect(Collectors.toCollection(() -> new LinkedList<>()));

        Set<String> s1 = strings.stream().collect(Collectors.toSet());
        Set<String> s2 = strings.stream().collect(Collectors.toUnmodifiableSet());

        Map<String, Integer> m1 = strings.stream().collect(Collectors.toMap(s -> s, String::length));
        Map<String, Integer> m2 = strings.stream().collect(Collectors.toMap(s -> s, String::length, (i1, i2) -> i1));

        Map<Integer, Set<String>> m3 = strings.stream().collect(Collectors.groupingBy(String::length, Collectors.toSet()));

        String s3 = strings.stream().collect(Collectors.joining());
        String s4 = strings.stream().collect(Collectors.joining(","));

        String s5 = strings.stream().collect(collectingAndThen(counting(), Object::toString));
    }
}
