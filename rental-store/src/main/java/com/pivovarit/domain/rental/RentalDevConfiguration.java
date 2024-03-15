package com.pivovarit.domain.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Configuration
@Profile("dev")
class RentalDevConfiguration {

    @Bean
    public MovieDescriptionsRepository inmemMovieDescriptionsRepository() {
        return id -> Optional.of(new MovieDescription("lorem ipsum"));
    }

    @Bean
    public MovieRepository inmemMovieRepository() {
        return new InMemoryMovieRepository();
    }

    @Bean
    public RentalHistoryRepository inmemRentalHistoryRepository() {
        return new InMemoryRentalHistory();
    }
}
