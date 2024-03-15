package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class L6_CachedThreadPool {

    private static final int THREADS = 4;
    private static final int EXTRA_THREADS = 4;
    public static final int Q_CAPACITY = 20;

    public static void main(String[] args) {

        var e1 = new ThreadPoolExecutor(1, 100,
          30, TimeUnit.SECONDS,
          new LinkedBlockingQueue<>(10),
          Thread.ofPlatform().name("mail-scheduler-", 0).factory());
        for (int i = 0; i < THREADS + Q_CAPACITY + EXTRA_THREADS + 10; i++) {
            int finalI = i;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            e1.submit(() -> {
                process(finalI);
            });
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

    public static void timed(Runnable action) {
        var before = Instant.now();
        action.run();
        var after = Instant.now();

        System.out.println("duration: " + Duration.between(before, after).toMillis() + "ms");
    }
}
