package com.for_comprehension.function.option;

import java.util.Objects;
import java.util.function.Function;


// to jest taki typ, który mówi "albo coś jest, albo nie ma"
public sealed interface Option<T> permits Some, None {

    boolean isPresent();

    boolean isEmpty();

    <U> Option<U> flatMap(Function<? super T, ? extends Option<U>> mapper);

    default <U> Option<U> map(Function<? super T, ? extends U> mapper) {

        Objects.requireNonNull(mapper, "mapper is null");
        return flatMap(t -> Option.of(mapper.apply(t)));
    }

    T orElse(T other);

    static <T> Option<T> of(T value) {
        return value == null ? (Option<T>) None.INSTANCE : new Some<>(value);
    }
}
