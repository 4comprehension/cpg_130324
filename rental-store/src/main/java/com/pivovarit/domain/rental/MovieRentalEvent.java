package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;

record MovieRentalEvent(long id, String timestamp, MovieRentalEventType type, MovieId movieId, String login) {
}
