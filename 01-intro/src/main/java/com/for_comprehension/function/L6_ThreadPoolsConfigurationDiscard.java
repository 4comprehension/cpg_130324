package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class L6_ThreadPoolsConfigurationDiscard {

    private static final int THREADS = 4;
    private static final int EXTRA_THREADS = 4;
    public static final int Q_CAPACITY = 20;

    public static void main(String[] args) {
        var e1 = new ThreadPoolExecutor(THREADS, THREADS + EXTRA_THREADS,
          30, TimeUnit.SECONDS,
          new ArrayBlockingQueue<>(Q_CAPACITY),
          Thread.ofPlatform().name("mail-scheduler-", 0).factory(), (r, executor) -> System.out.println("silently discarding task"));
        for (int i = 0; i < THREADS + Q_CAPACITY + EXTRA_THREADS + 10; i++) {
            int finalI = i;
            e1.submit(() -> {
                process(finalI);
            });
        }
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(1000);
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
