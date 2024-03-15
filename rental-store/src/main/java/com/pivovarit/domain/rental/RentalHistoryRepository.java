package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;

import java.util.List;

interface RentalHistoryRepository {
    void saveRentEvent(MovieId id, String login, long eventId);

    void saveReturnEvent(MovieId id, String login, long eventId);

    /**
     * @return 0, if there are no events in the database
     */
    long lastEventId();

    List<MovieRentalEvent> findAll();

    List<MovieRentalEvent> findUserRentals(String login);

    List<MovieRentalEvent> findMovieRentals(MovieId movieId);

    List<MovieRentalEvent> getUnprocessed();

    void markProcessed(MovieRentalEvent event);
}
