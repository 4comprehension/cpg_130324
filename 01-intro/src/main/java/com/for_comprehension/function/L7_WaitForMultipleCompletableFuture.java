package com.for_comprehension.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class L7_WaitForMultipleCompletableFuture {

    public static void main(String[] args) {
        try (var e = Executors.newFixedThreadPool(4)) {
            List<CompletableFuture<String>> futures = Collections.synchronizedList(new ArrayList<>());

            for (int i = 0; i < 4; i++) {
                int finalI = i;
                futures.add(CompletableFuture.supplyAsync(() -> process("foo-" + finalI), e));
            }


            CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0]))
              .thenAccept(System.out::println);

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
