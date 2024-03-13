package com.for_comprehension.function.template;

class TemplateMethodLambda {

    public static void main(String[] args) {
        runWithLogging(() -> System.out.println("hello!"));
    }

    public static void runWithLogging(Runnable action) {
        System.out.println("entering a method!");
        action.run();
        System.out.println("exiting a method!");
    }
}
