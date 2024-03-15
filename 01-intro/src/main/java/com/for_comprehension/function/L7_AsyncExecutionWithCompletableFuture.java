package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

class L7_AsyncExecutionWithCompletableFuture {

    public static void main(String[] args) {
        try (var e = Executors.newFixedThreadPool(5)) {
            var cf1 = CompletableFuture.supplyAsync(() -> 1, e);
            var cf2 = CompletableFuture.supplyAsync(() -> process(17), e);
            cf1
              .thenApplyAsync(i -> {
                  System.out.println(Thread.currentThread().getName());
                  return i + 1;
              }, e) // .map(i -> i + 1)
              .applyToEitherAsync(cf2, i -> {
                    System.out.println(Thread.currentThread().getName());
                    return i + 1;
                }, e
              )
//              .thenCombine(cf2, Integer::sum)
//              .thenCompose(i -> CompletableFuture.supplyAsync(() -> i)
              .thenAcceptAsync(System.out::println, e)
              .thenRunAsync(() -> System.out.println("done from " + Thread.currentThread().getName()), e);
            System.out.println("idziemy robić coś innego");
        }
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public static <T> T timed(Supplier<T> action) {
        var before = Instant.now();
        T result = action.get();
        var after = Instant.now();

        System.out.printf("duration: %dms%n", Duration.between(before, after).toMillis());
        return result;
    }
}
