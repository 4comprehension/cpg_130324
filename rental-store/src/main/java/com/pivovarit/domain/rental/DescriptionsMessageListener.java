package com.pivovarit.domain.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivovarit.domain.rental.api.MovieId;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

@RequiredArgsConstructor
class DescriptionsMessageListener implements MessageListener {

    private final MovieDescriptionsUpdater descriptionsUpdater;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message) {
        try {
            var descriptionUpdated = objectMapper.readValue(message.getBody(), DescriptionUpdated.class);
            descriptionsUpdater.onDescriptionUpdated(new MovieId(descriptionUpdated.movieId()), descriptionUpdated.description());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    record DescriptionUpdated(long movieId, String description) {
    }
}
