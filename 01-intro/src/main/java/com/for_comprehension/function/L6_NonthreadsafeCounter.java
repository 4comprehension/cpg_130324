package com.for_comprehension.function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class L6_NonthreadsafeCounter {

    private static volatile int counter = 0;

    public static void main(String[] args) {
        try (var e = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 10; i++) {
                e.submit(() -> {
                    counter++;
                    System.out.println("counter: " + counter);
                });
            }
        }
    }
}
