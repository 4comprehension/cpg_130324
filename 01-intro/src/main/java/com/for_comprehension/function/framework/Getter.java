package com.for_comprehension.function.framework;

public interface Getter<T> {
    T get();

    static Getter instance() {
        return new Getter() {
            @Override
            public Object get() {
                return null;
            }
        };
    }
}
