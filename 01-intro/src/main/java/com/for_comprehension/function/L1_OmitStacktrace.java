package com.for_comprehension.function;

import java.util.Arrays;

class L1_OmitStacktrace {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                Thread.sleep(1);
                print(null);
            } catch (NullPointerException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static void print(String foo) {
        System.out.println(foo.toUpperCase());
    }
}
