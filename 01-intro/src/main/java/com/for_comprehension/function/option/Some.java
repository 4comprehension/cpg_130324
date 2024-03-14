package com.for_comprehension.function.option;

import java.util.Objects;
import java.util.function.Function;

public record Some<T>(T value) implements Option<T> {

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public <U> Option<U> flatMap(Function<? super T, ? extends Option<U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return mapper.apply(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }
}
