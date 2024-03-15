package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class L6_ThreadPoolsAutoclosable {

    public static void main(String[] args) {
        try (var e1 = Executors.newFixedThreadPool(2)) {
            AtomicInteger counter = new AtomicInteger(0);

            e1.submit(() -> {
                process(counter.incrementAndGet());
            });
            e1.submit(() -> {
                process(counter.incrementAndGet());
            });
            e1.submit(() -> {
                process(counter.incrementAndGet());
            });
            e1.submit(() -> {
                process(counter.incrementAndGet());
            });
            e1.submit(() -> {
                process(counter.incrementAndGet());
            });
        }
        System.out.println("pool shut down!");
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public static <T> T timed(Supplier<T> action) {
        var before = Instant.now();
        T result = action.get();
        var after = Instant.now();

        System.out.println("duration: " + Duration.between(before, after).toMillis() + "ms");
        return result;
    }
}
