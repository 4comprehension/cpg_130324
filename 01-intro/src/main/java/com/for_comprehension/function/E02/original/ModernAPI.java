package com.for_comprehension.function.E02.original;

import java.util.Optional;

class ModernAPI {

    private final LegacyAPI delegate;

    ModernAPI(LegacyAPI delegate) {
        this.delegate = delegate;
    }

    Optional<LegacyAPI.Person> findPerson(int id) {
        return Optional.ofNullable(delegate.findPerson(id));
    }

    Optional<String> findAddress(LegacyAPI.Person person) {
        return Optional.ofNullable(delegate.findAddress(person));
    }

    Optional<String> findAddressById(int id) {
        return Optional.ofNullable(delegate.findAddressById(id));
    }
}
