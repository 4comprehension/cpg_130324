package com.for_comprehension.function;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class L1_Function {

    /*
    (String a, Integer b) -> {
        //...
        return a + b;
    }

    (a, b) -> {
        return a + b;
    }

    (a, b) -> a + b

    a -> a + 1

    () -> 42

    (var a, var b) -> a + b

    // JDK 22
    (a, _, _) -> a + 2

    // lambda: str -> str.toUpperCase()
    // method ref: String::toUpperCase
     */
    public static void main(String[] args) {
        new Thread(() -> System.out.println("Hello!"));

        Function<Integer, String> f1 = i -> i.toString();
        Function<Integer, String> f1_mr = Object::toString;

        Consumer<Integer> f2 = i -> System.out.println(i);  // Function<Integer, Void>
        Supplier<Integer> f3 = () -> 42; // Function<Void, Integer>

        Integer r1 = calculateResult();
        Supplier<Integer> r2 = () -> calculateResult();

        Predicate<Integer> f4 = i -> i % 2 == 0; // Function<Integer, Boolean>
        UnaryOperator<Integer> f5 = i -> i + 1;   // Function<Integer, Integer>
        Runnable f6 = () -> System.out.println("Hello!"); // Function<Void, Void>
        Callable<Integer> f7 = () -> 42; // Function<Void, Integer>

        BiFunction<Integer, Integer, Integer> f8 = (i1, i2) -> i1 + i2;
        BiFunction<Integer, Integer, Integer> f8_mf = Integer::sum;

        BinaryOperator<Integer> f9 = (i1, i2) -> Math.min(i1, i2);

        TriFunction<Integer, Integer, Integer, Integer> tf = (i1, i2, i3) -> i1 + i2 + i3;
        TriFunction<String, String, Integer, Object> tf2 = (s, s2, integer) -> s + s2 + integer;
        Integer result = tf.apply(1, 2, 3);
    }

    @FunctionalInterface
    public interface TriFunction<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);

        default <V> TriFunction<T1, T2, T3, V> andThen(Function<R, V> after) {
            Objects.requireNonNull(after);
            return (t1, t2, t3) -> after.apply(apply(t1, t2, t3));
        }
    }

    public static void calculate(Supplier<Integer> value) {
        if (new Random().nextInt() % 2 == 0) {
            System.out.println(value.get());
        }
    }

    public static int calculateResult() {
        try {
            System.out.println("calculating...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 42;
    }
}
