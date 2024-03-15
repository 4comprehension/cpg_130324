package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;

class L8_VirtualThreadsThreadPinningIssue {
    public static void main(String[] args) {
        var e = Executors.newVirtualThreadPerTaskExecutor();
        var result = timed(() -> Stream.iterate(0, i -> i + 1)
          .limit(1000)
          .map(i -> CompletableFuture.supplyAsync(() -> process(i), e))
          .collect(collectingAndThen(Collectors.toList(), L8_VirtualThreadsThreadPinningIssue::asList))
          .join());
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            synchronized (new Object()) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public static <T> T timed(Supplier<T> action) {
        var before = Instant.now();
        T result = action.get();
        var after = Instant.now();

        System.out.println("duration: " + Duration.between(before, after).toMillis() + "ms");
        return result;
    }

    static <T> CompletableFuture<List<T>> asList(List<CompletableFuture<T>> futures) {
        // https://shipilev.net/blog/2016/arrays-wisdom-ancients/
        var result = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        for (var future : futures) {
            future.whenComplete((__, throwable) -> {
                if (throwable != null) {
                    result.completeExceptionally(throwable);
                }
            });
        }

        return result.thenApply(__ -> futures)
          .thenApply(f -> f.stream()
            .map(CompletableFuture::resultNow)
            .toList());
    }
}
