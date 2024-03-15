package com.pivovarit.domain.descriptions;

import com.pivovarit.domain.descriptions.api.DescriptionUpdatedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
class NoopMessagePublisher implements MessagePublisher {
    @Override
    public void publish(DescriptionUpdatedEvent event) {

    }
}
