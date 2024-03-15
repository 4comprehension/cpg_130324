package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

record HttpMovieDescriptionsRepository(RestTemplate restTemplate,
                                       String baseUrl)
  implements MovieDescriptionsRepository {

    @Override
    public Optional<MovieDescription> findByMovieId(MovieId movieId) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(baseUrl + "/descriptions/{id}", MovieDescription.class, movieId.id()));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
