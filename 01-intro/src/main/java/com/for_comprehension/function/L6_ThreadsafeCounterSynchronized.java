package com.for_comprehension.function;

import java.util.concurrent.Executors;

class L6_ThreadsafeCounterSynchronized {

    private static volatile int counter = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        try (var e = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 10; i++) {
                e.submit(() -> {
                    synchronized (lock) {
                        counter++;
                        System.out.println("counter: " + counter);
                    }
                });
            }
        }
    }
}
