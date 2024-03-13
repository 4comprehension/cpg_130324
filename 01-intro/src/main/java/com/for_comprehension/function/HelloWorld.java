package com.for_comprehension.function;

import java.util.List;
import java.util.Map;

class HelloWorld {
    public static void main(String[] args) {

        Map.of("foo", "bar")
          .forEach((k, v) -> System.out.println(k));
    }
}
