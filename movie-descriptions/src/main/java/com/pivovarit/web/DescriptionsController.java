package com.pivovarit.web;

import com.pivovarit.domain.descriptions.MovieDescriptionsFacade;
import com.pivovarit.domain.descriptions.api.MovieDescription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DescriptionsController {

    private final MovieDescriptionsFacade movieDescriptions;

    DescriptionsController(MovieDescriptionsFacade movieDescriptions) {
        this.movieDescriptions = movieDescriptions;
    }

    @GetMapping("/descriptions/{movieId}")
    public ResponseEntity<MovieDescription> getDescription(@PathVariable long movieId) {
        return movieDescriptions.findByMovieId(movieId)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
