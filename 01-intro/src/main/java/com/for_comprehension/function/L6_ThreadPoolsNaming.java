package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class L6_ThreadPoolsNaming {

    public static void main(String[] args) {
        var e1 = Executors.newFixedThreadPool(1000, Thread.ofPlatform().name("mail-scheduler-", 0).factory());
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < 1000; i++) {
            e1.submit(() -> {
                if (counter.get() == 500) {
                    String name = Thread.currentThread().getName();
                    Thread.currentThread().setName("tutaj się coś dzieje");
                    process(counter.incrementAndGet());
                    Thread.currentThread().setName(name);
                } else {
                    process(counter.incrementAndGet());
                }
            });
        }
    }

    // very useful before JDK 21
    private static ThreadFactory named(String prefix) {
        return new ThreadFactory() {

            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "%s-%d".formatted(prefix, counter.incrementAndGet()));
            }
        };
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(60000);
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
