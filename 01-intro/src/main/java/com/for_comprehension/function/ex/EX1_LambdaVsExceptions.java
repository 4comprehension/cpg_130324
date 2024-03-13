package com.for_comprehension.function.ex;

import java.util.function.Function;
import java.util.stream.Stream;

class EX1_LambdaVsExceptions {

    public static void main(String[] args) {
        rethrow(new Exception("Hello, world!"));

        Stream.of(1, 2, 3)
          .map(unchecked(i -> process(i)))
          .map(sneaky(i -> process(i)))
          .forEach(System.out::println);
    }

    public static <T extends Exception, R> R rethrow(Exception ex) throws T {
        throw (T) ex;
    }

    public static <T, R> Function<T, R> sneaky(ThrowingFunction<T, R> action) {
        return t -> {
            try {
                return action.apply(t);
            } catch (Exception e) {
                return rethrow(e);
            }
        };
    }

    public static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> action) {
        return t -> {
            try {
                return action.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    public interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }

    public static int process(int i) throws Exception {
        return i;
    }
}
