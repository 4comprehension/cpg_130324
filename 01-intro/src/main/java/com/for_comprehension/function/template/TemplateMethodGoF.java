package com.for_comprehension.function.template;

class TemplateMethodGoF {

    public static void main(String[] args) {

        new LoggingTemplateMethod(){
            @Override
            void internalRun() {
                System.out.println("hello!");
            }
        }.run();

    }

    public static class HelloWorldWithLogging extends LoggingTemplateMethod {

        @Override
        void internalRun() {
            System.out.println("Hello World!");
        }
    }


    public static abstract class LoggingTemplateMethod {
        void run() {
            System.out.println("entering a method!");
            internalRun();
            System.out.println("exiting a method!");
        }
        abstract void internalRun();
    }
}
