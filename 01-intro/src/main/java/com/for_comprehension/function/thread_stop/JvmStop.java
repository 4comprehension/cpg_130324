package com.for_comprehension.function.thread_stop;

class JvmStop {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
