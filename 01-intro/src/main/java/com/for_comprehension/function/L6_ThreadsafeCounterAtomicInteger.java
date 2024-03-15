package com.for_comprehension.function;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class L6_ThreadsafeCounterAtomicInteger {

    public static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        try (var e = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 10; i++) {
                e.submit(() -> {
                    System.out.println("counter: " +  counter.getAndIncrement());
                });
            }
        }
    }
}
