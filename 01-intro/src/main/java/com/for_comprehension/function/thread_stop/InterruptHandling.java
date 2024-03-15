package com.for_comprehension.function.thread_stop;

class InterruptHandling {

    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
