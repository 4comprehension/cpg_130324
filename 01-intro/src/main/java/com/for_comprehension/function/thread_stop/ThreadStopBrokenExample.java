package com.for_comprehension.function.thread_stop;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

// demo does not work on JDK 21
class ThreadStopBrokenExample {

    private static final ConcurrentHolder holder = new ConcurrentHolder();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                holder.process(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                holder.process(i);
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                holder.process(i);
            }
        });


        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2_000);

        t1.stop();

        Thread.sleep(5_000);

        t2.stop();

        Thread.sleep(5_000);

        t3.stop();
    }


    public static class ConcurrentHolder {
        private final AtomicInteger state = new AtomicInteger(0);

        public synchronized void process(int input) {
            // do some work
            int i = input + state.get();
            System.out.println("processing " + i + " on " + Thread.currentThread().getName());
            state.getAndSet(new Random().nextInt());

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            state.set(0);
        }
    }
}
