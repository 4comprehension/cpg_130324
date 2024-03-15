package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieAddRequest;
import com.pivovarit.domain.rental.api.MovieDto;
import com.pivovarit.domain.rental.api.MovieId;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MovieServiceTest {

    private static final int SPIDERMAN_ID = 1;
    private static final String SPIDERMAN_DESCRIPTION = "Spiderman description";

    @RepeatedTest(1000)
    void shouldSaveMovie() throws Exception {
        RentalFacade service = inMemInstance();
        MovieAddRequest movie = new MovieAddRequest(SPIDERMAN_ID, "Spiderman", "NEW");

        service.addMovie(movie);

        MovieDto result = service.findMovieById(new MovieId(movie.id())).orElseThrow();

        assertThat(result.id()).isEqualTo(movie.id());
        assertThat(result.title()).isEqualTo(movie.title());
        assertThat(result.type()).isEqualTo(movie.type());
        assertThat(result.description()).isEqualTo(SPIDERMAN_DESCRIPTION);
    }

    private static RentalFacade inMemInstance() {
        var movieRepository = new InMemoryMovieRepository();
        var movieRentalService = new MovieRentalService(new InMemoryRentalHistory(), movieRepository);
        MovieDescriptionsRepository movieDescriptionsRepository = id -> id.id() == SPIDERMAN_ID ? Optional.of(new MovieDescription(SPIDERMAN_DESCRIPTION)) : Optional.of(new MovieDescription(""));
        return new RentalFacade(
          movieRepository,
          movieDescriptionsRepository,
          movieRentalService);
    }
}
