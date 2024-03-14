package com.for_comprehension.function.jmm;

class L4_Volatile {

    private static volatile int counter;

    public static void main(String[] args) {
            new Thread(() -> {
                while (true) {
                    counter++;
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    System.out.println(counter);
                }
            }).start();
    }
}
