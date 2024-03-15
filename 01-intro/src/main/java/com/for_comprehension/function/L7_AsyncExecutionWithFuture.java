package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

class L7_AsyncExecutionWithFuture {

    public static void main(String[] args) {
        try (var e = Executors.newFixedThreadPool(5)) {
            Future<Integer> future = e.submit(() -> process(42));
            System.out.println("idziemy robić coś innego");
            // ...

            Integer result = future.get();
            System.out.println(result);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public static <T> T timed(Supplier<T> action) {
        var before = Instant.now();
        T result = action.get();
        var after = Instant.now();

        System.out.printf("duration: %dms%n", Duration.between(before, after).toMillis());
        return result;
    }
}
