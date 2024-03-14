package com.for_comprehension.function.jmm;

class L4_NonAtomicNonVolatileLongWrite {

    // without volatile, long can be written in two 32bit steps
    private static volatile long counter;

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                counter = Long.MAX_VALUE;
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println(counter);
            }
        }).start();
    }
}
