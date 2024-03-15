package com.for_comprehension.function;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class L4_StreamParallelSaturated {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            IntStream.range(0, 20).boxed()
              .parallel()
              .map(i -> {
                  try {
                      Thread.sleep(1000000);
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }

                    return i;
              }).toList();
        }).start();

        Thread.sleep(1000);

        List<Integer> r = timed(() -> Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
          .parallel()
          .map(i -> process(i))
          .toList());

        System.out.println(r);
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

    public static <T> T timed(Supplier<T> action) {
        var before = Instant.now();
        T result = action.get();
        var after = Instant.now();

        System.out.println("duration: " + Duration.between(before, after).toMillis() + "ms");
        return result;
    }
}
