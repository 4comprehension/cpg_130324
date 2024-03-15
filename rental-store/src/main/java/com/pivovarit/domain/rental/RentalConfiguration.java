package com.pivovarit.domain.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
class RentalConfiguration {

    @Bean
    public MovieRentalService movieRentalService(RentalHistoryRepository rentalHistoryRepository, MovieRepository movieRepository) {
        return new MovieRentalService(rentalHistoryRepository, movieRepository);
    }

    @Bean
    public RentalFacade rentalFacade(MovieRepository movieRepository, MovieDescriptionsRepository movieDescriptions, MovieRentalService movieRentalService) {
        return new RentalFacade(movieRepository, movieDescriptions, movieRentalService);
    }
}
