package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class InMemoryRentalHistory implements RentalHistoryRepository {

    private final List<MovieRentalEvent> events = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void saveRentEvent(MovieId id, String login, long eventId) {
        events.add(new MovieRentalEvent(events.size() + 1, Instant.now()
          .toString(), MovieRentalEventType.MOVIE_RENTED, id, login));
    }

    @Override
    public void saveReturnEvent(MovieId id, String login, long eventId) {
        events.add(new MovieRentalEvent(events.size() + 1, Instant.now()
          .toString(), MovieRentalEventType.MOVIE_RETURNED, id, login));
    }

    @Override
    public long lastEventId() {
        return events.size();
    }

    @Override
    public List<MovieRentalEvent> findAll() {
        return List.copyOf(events);
    }

    @Override
    public List<MovieRentalEvent> findUserRentals(String login) {
        return events.stream().filter(e -> e.login().equals(login)).toList();
    }

    @Override
    public List<MovieRentalEvent> findMovieRentals(MovieId movieId) {
        return events.stream().filter(e -> e.movieId().equals(movieId)).toList();
    }

    @Override
    public List<MovieRentalEvent> getUnprocessed() {
        return findAll();
    }

    @Override
    public void markProcessed(MovieRentalEvent event) {

    }
}
