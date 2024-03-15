package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;
import lombok.Data;

import java.util.Optional;

@Data
final class Movie implements RentalAggregate {

    private final MovieId id;
    private final String title;
    private final MovieType type;

    private String renter;

    public boolean canBeRented() {
        return renter == null;
    }

    public boolean canBeReturned(String login) {
        return renter != null && renter.equals(login);
    }

    @Override
    public void apply(MovieRentalEvent event) {
        switch (event.type()) {
            case MOVIE_RENTED -> renter = event.login();
            case MOVIE_RETURNED -> renter = null;
        }
    }

    Optional<String> getRenter() {
        return Optional.ofNullable(renter);
    }
}
