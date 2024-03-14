package com.for_comprehension.function;

class L5_Threads {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hello from a thread!");
            }
        });
        thread.start();

        thread.stop();


        System.out.println("Hello from main!");
    }
}
