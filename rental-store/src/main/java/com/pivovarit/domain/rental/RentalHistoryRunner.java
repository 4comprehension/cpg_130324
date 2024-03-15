package com.pivovarit.domain.rental;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
record RentalHistoryRunner(RentalHistoryRepository repository) implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        repository.findAll().forEach(System.out::println);
    }
}
