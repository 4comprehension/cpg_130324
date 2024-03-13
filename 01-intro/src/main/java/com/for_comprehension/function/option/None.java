package com.for_comprehension.function.option;

import java.util.function.Function;

final class None<T> implements Option<T> {

    static None<?> INSTANCE = new None<>();

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public <U> Option<U> flatMap(Function<? super T, ? extends Option<U>> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }
}
