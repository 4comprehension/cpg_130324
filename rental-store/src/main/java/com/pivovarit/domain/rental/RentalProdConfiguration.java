package com.pivovarit.domain.rental;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("prod")
class RentalProdConfiguration {

    @Bean
    public MovieRepository jdbcMovieRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcMovieRepository(jdbcTemplate);
    }

    @Bean
    public MovieDescriptionsRepository httpMovieDescriptionsRepository(RestTemplateBuilder builder, @Value("${movie-descriptions.url}") String descriptionsServiceUrl) {
        return new HttpMovieDescriptionsRepository(builder.build(), descriptionsServiceUrl);
    }

    @Bean
    public RentalHistoryRepository jdbcRentalHistoryRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRentalHistoryRepository(jdbcTemplate);
    }
}
