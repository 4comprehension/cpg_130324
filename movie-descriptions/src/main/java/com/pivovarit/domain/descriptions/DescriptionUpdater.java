package com.pivovarit.domain.descriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

//@Component
@RequiredArgsConstructor
class DescriptionUpdater implements ApplicationRunner {

    private final MovieDescriptionsFacade facade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            int millis = ThreadLocalRandom.current().nextInt(5000);
            Thread.sleep(millis);
            facade.updateDescription(millis, "foo var");
        }
    }
}
