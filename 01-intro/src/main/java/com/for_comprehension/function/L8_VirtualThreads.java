package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;

class L8_VirtualThreads {

    // cached
    // 10x - 10010ms
    // 100x - 10016ms
    // 1000x - 10051ms
    // 5000x - 10398ms
    // 10000x - unable to create native thread

    // virtual
    // 10x - 10017ms
    // 100x - 10018ms
    // 1000x - 10047ms
    // 5000x - 10386ms
    // 10000x - 10602ms
    // 100000x - 11730ms
    // 1000000x - 22737ms
    // 5000000 - 61290ms
    public static void main(String[] args) {
        var e = Executors.newVirtualThreadPerTaskExecutor();
        var result = timed(() -> Stream.iterate(0, i -> i + 1)
          .limit(5000000)
          .map(i -> CompletableFuture.supplyAsync(() -> process(i), e))
          .collect(collectingAndThen(Collectors.toList(), L8_VirtualThreads::asList))
          .join());
    }

    public static <T> T process(T input) {
        try {
            System.out.printf("processing: %s on %s%n", input, Thread.currentThread().getName());
            Thread.sleep(10000);
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
