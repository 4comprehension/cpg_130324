package com.for_comprehension.function.template;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

class TemplateMethodLambdaTimed {
    public static void main(String[] args) {
        var result = timed(() -> calculate());
        System.out.println("result: " + result);

        var result2 = timed(() -> "foo");
        System.out.println("result2: " + result2);
    }

    public static <T> T timed(Supplier<T> action) {
        var before = Instant.now();
        T result = action.get();
        var after = Instant.now();

        System.out.println("duration: " + Duration.between(before, after).toNanos() + "ns");
        return result;
    }

    public static <T> Optional<T> timedUnchecked(Callable<T> action) {
        var before = Instant.now();
        T result = null;
        try {
            result = action.call();
        } catch (Exception e) {
            System.out.println("duration: " + Duration.between(before, Instant.now()).toNanos() + "ns");
            return Optional.empty();
        }

        System.out.println("duration: " + Duration.between(before, Instant.now()).toNanos() + "ns");

        return Optional.ofNullable(result);
    }

    public static void runWithLogging(Runnable action) {
        System.out.println("entering a method!");
        action.run();
        System.out.println("exiting a method!");
    }

    public static int calculate() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return 42;
    }
}
