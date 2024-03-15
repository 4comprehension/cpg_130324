package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;
import com.pivovarit.domain.rental.api.exception.CannotReturnException;
import com.pivovarit.domain.rental.api.exception.MovieAlreadyRentedException;
import com.pivovarit.domain.rental.api.exception.MovieNotExistsException;
import com.pivovarit.domain.rental.api.exception.UserCannotRentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
class MovieRentalService {

    private final RentalHistoryRepository rentalHistoryRepository;
    private final MovieRepository movieRepository;

    void rentMovie(String login, MovieId id) {
        retryOnConcurrentWrite(() -> {
            long lastEventId = rentalHistoryRepository.lastEventId();

            Movie movie = reconstructMovie(id);
            UserRentals userRentals = reconstructUserRentals(login);

            if (!movie.canBeRented()) {
                throw new MovieAlreadyRentedException(id);
            }
            if (!userRentals.canRent()) {
                throw new UserCannotRentException(login);
            }

            log.info("user rental history: {}", userRentals);
            log.info("rent request:: login: {}, id: {}", login, id.id());
            rentalHistoryRepository.saveRentEvent(id, login, lastEventId + 1);
        });
    }

    void returnMovie(String login, MovieId id) {
        retryOnConcurrentWrite(() -> {
            long lastEventId = rentalHistoryRepository.lastEventId();

            Movie movie = reconstructMovie(id);
            UserRentals userRentals = reconstructUserRentals(login);

            if (!userRentals.canReturn(id) || !movie.canBeReturned(login)) {
                throw new CannotReturnException(id);
            }

            log.info("user rental history: {}", userRentals);
            log.info("return request:: login: {}, id: {}", login, id.id());
            rentalHistoryRepository.saveReturnEvent(id, login, lastEventId + 1);
            // update elasticsearch, data lake
        });
    }

    private static void retryOnConcurrentWrite(Runnable action) {
        var success = false;
        var retries = 0;
        while (!success) {
            try {
                action.run();
                success = true;
            } catch (ConcurrentLogWriteException e) {
                retries++;
                if (retries > 20) {
                    throw new RuntimeException("failed to update data store after multiple retries", e);
                }
                log.warn("Concurrent data store write detected, retrying... ({} times)", retries);
            }
        }
    }

    private Movie reconstructMovie(MovieId id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotExistsException(id));
        List<MovieRentalEvent> movieRentalEvents = rentalHistoryRepository.findMovieRentals(id);
        // worth considering applying only the last event since it contains all necessary information at the moment
        movieRentalEvents.forEach(movie::apply);
        return movie;
    }

    private UserRentals reconstructUserRentals(String login) {
        List<MovieRentalEvent> userEvents = rentalHistoryRepository.findUserRentals(login);
        UserRentals userRentals = new UserRentals(login);
        userRentals.apply(userEvents);
        return userRentals;
    }
}
