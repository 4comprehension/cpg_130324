package com.for_comprehension.function.option;

class JDK21ModernSwitch {

    public static void main(String[] args) {
        Option<String> option = Option.of("foo");

        switch (option) {
            case Some<String>(String value) -> System.out.println(value);
            case None<String> none -> System.out.println(none.orElse("baz"));
        }
    }
}
