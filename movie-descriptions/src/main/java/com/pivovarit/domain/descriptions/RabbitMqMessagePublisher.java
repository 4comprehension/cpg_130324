package com.pivovarit.domain.descriptions;

import com.pivovarit.domain.descriptions.api.DescriptionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
class RabbitMqMessagePublisher implements MessagePublisher {

    private final RabbitTemplate rmq;

    @Override
    public void publish(DescriptionUpdatedEvent event) {
        rmq.convertAndSend("movie-descriptions-topic", DescriptionUpdatedEvent.class.getName(), event);
    }
}
