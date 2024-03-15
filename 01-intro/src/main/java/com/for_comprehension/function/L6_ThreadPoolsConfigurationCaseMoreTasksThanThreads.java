package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class L6_ThreadPoolsConfigurationCaseMoreTasksThanThreads {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final int THREADS = 4;

    public static void main(String[] args) {
        timed(() -> {
            try (var e1 = Executors.newFixedThreadPool(THREADS, Thread.ofPlatform().name("mail-scheduler-", 0)
              .factory())) {
                for (int i = 0; i < THREADS + 20; i++) {
                    e1.submit(() -> {
                        process(counter.incrementAndGet());
                    });
                }
            }
        });
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public static void timed(Runnable action) {
        var before = Instant.now();
        action.run();
        var after = Instant.now();

        System.out.println("duration: " + Duration.between(before, after).toMillis() + "ms");
    }
}
