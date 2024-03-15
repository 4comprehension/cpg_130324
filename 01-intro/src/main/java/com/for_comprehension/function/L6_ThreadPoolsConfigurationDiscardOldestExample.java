package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
38 tasków

4 --> bezpośrednio na wątki
    processing: 0 on mail-scheduler-0
    processing: 1 on mail-scheduler-1
    processing: 2 on mail-scheduler-2
    processing: 3 on mail-scheduler-3

20 --> do kolejki
4 --> przepełniają kolejkę, pojawiają się nowe wątki i od razu je wykonują
    processing: 24 on mail-scheduler-4
    processing: 25 on mail-scheduler-5
    processing: 26 on mail-scheduler-6
    processing: 27 on mail-scheduler-7

10 --> odrzucone + discard oldest policy, odpadaja kolejne elementy od 4 do 13 włącznie i reszta leci "normalnie"
    processing: 18 on mail-scheduler-1
    processing: 15 on mail-scheduler-4
    processing: 19 on mail-scheduler-7
    processing: 17 on mail-scheduler-6
    processing: 16 on mail-scheduler-2
    processing: 14 on mail-scheduler-5
    processing: 20 on mail-scheduler-3
    processing: 21 on mail-scheduler-0
    processing: 22 on mail-scheduler-3
    processing: 23 on mail-scheduler-4
    processing: 28 on mail-scheduler-7
    processing: 29 on mail-scheduler-2
    processing: 30 on mail-scheduler-6
    processing: 31 on mail-scheduler-1
    processing: 32 on mail-scheduler-5
    processing: 33 on mail-scheduler-0
    processing: 34 on mail-scheduler-2
    processing: 35 on mail-scheduler-4
    processing: 36 on mail-scheduler-6
    processing: 37 on mail-scheduler-7








 */
class L6_ThreadPoolsConfigurationDiscardOldestExample {

    private static final int THREADS = 4;
    private static final int EXTRA_THREADS = 4;
    public static final int Q_CAPACITY = 20;

    public static void main(String[] args) {
        var e1 = new ThreadPoolExecutor(THREADS, THREADS + EXTRA_THREADS,
          30, TimeUnit.SECONDS,
          new ArrayBlockingQueue<>(Q_CAPACITY),
          Thread.ofPlatform().name("mail-scheduler-", 0).factory(), new ThreadPoolExecutor.DiscardOldestPolicy());
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
