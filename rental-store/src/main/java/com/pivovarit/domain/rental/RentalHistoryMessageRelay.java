package com.pivovarit.domain.rental;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
class RentalHistoryMessageRelay {

    private final RentalHistoryRepository rentalHistoryRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 1000)
    public void processEvents() {
        timed(() -> {
            rentalHistoryRepository.getUnprocessed()
              .forEach(event -> {
                  rabbitTemplate.convertAndSend("rental-history-topic", MovieRentalEvent.class.getName(), event);
                  rentalHistoryRepository.markProcessed(event);
              });
        });
    }

    public static <T> void timed(Runnable runnable) {
        Instant before = Instant.now();
        runnable.run();
        Instant after = Instant.now();

        log.info("duration: {}ms", Duration.between(before, after).toMillis());
    }
}
