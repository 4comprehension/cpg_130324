package com.pivovarit.domain.descriptions;

import com.pivovarit.domain.descriptions.api.DescriptionUpdatedEvent;

interface MessagePublisher {

    void publish(DescriptionUpdatedEvent event);
}
