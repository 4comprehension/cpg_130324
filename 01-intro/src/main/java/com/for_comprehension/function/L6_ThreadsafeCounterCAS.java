package com.for_comprehension.function;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.Executors;

class L6_ThreadsafeCounterCAS {

    public static volatile int counter = 0;

    public static void main(String[] args) {
        VarHandle counterVarHandle = getCounterVarHandle();

        try (var e = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 10; i++) {
                e.submit(() -> {
                    int value = (int) counterVarHandle.getVolatile();
                    while (!counterVarHandle.compareAndSet(value, value + 1)) {
                        value = (int) counterVarHandle.getVolatile();
                    }
                    System.out.println("counter: " + value);
                });
            }
        }
    }

    private static VarHandle getCounterVarHandle() {
        // get var handle for counter
        try {
            return MethodHandles.lookup()
              .findStaticVarHandle(L6_ThreadsafeCounterCAS.class, "counter", int.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
