package com.for_comprehension.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class L7_WaitForMultiple {

    public static void main(String[] args) {
        try (var e = Executors.newFixedThreadPool(4)) {
            List<Future<String>> futures = Collections.synchronizedList(new ArrayList<>());

            for (int i = 0; i < 4; i++) {
                int finalI = i;
                futures.add(e.submit(() -> process("foo-" + finalI)));
            }


            while (true) {
                boolean finished = false;
                for (Future<String> future : futures) {
                    if (future.isDone()) {
                        try {
                            System.out.println(future.get());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        ArrayList<Future<String>> copy = new ArrayList<>(futures);
                        copy.remove(future);
                        for (Future<String> stringFuture : copy) {
                            stringFuture.cancel(true);
                        }
                        finished = true;
                        break;
                    }
                }
                if (finished) {
                    break;
                }
                Thread.onSpinWait();
            }
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
}
