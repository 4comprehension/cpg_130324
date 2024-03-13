package com.for_comprehension.function.template;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

class TemplateMethodLambdaTimed {

    public static void main(String[] args) {
        int result = timed(() ->calculate());
        // w logach: duration: XXX ms
    }

    public static ??? timed(??? action) {
        // ???
    }

    public static void runWithLogging(Runnable action) {
        System.out.println("entering a method!");
        action.run();
        System.out.println("exiting a method!");
    }

    public static int calculate() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return 42;
    }
}
