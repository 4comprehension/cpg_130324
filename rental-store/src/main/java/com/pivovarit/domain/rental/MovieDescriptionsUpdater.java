package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MovieDescriptionsUpdater {

    public void onDescriptionUpdated(MovieId id, String description) {
        // update lokalnego store'a z opisami filmów
        log.info("movie description updated: {}:{}", id.id(), description);
    }
}
