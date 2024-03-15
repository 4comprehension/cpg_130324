package com.pivovarit.domain.descriptions;

import com.pivovarit.domain.descriptions.api.DescriptionUpdatedEvent;
import com.pivovarit.domain.descriptions.api.MovieDescription;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MovieDescriptionsFacade {

    private final Map<Long, MovieDescription> descriptions = new ConcurrentHashMap<>();

    private final MessagePublisher messagePublisher;

    MovieDescriptionsFacade(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
        init();
    }

    private void init() {
        descriptions.put(1L, new MovieDescription("After being bitten by a genetically-modified spider, a shy teenager gains spider-like abilities that he uses to fight injustice as a masked superhero"));
        descriptions.put(2L, new MovieDescription("During WWII, Rick, a nightclub owner in Casablanca, agrees to help his former lover Ilsa and her husband. Soon, Ilsa's feelings for Rick resurface and she finds herself renewing her love for him."));
        descriptions.put(3L, new MovieDescription("During World War II, Lt. Gen. Leslie Groves Jr. appoints physicist J. Robert Oppenheimer to work on the top-secret Manhattan Project. Oppenheimer and a team of scientists spend years developing and designing the atomic bomb. "));
    }

    public Optional<MovieDescription> findByMovieId(long movieId) {
        return Optional.ofNullable(descriptions.get(movieId));
    }

    public void updateDescription(long movieId, String description) {
        descriptions.put(movieId, new MovieDescription(description));
        messagePublisher.publish(new DescriptionUpdatedEvent(movieId, description));
    }
}
