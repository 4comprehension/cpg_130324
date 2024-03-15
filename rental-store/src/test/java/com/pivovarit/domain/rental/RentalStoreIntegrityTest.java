package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieAddRequest;
import com.pivovarit.domain.rental.api.MovieId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Verifies that event store acts on the most recent aggregate version
 */
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("prod")
@Testcontainers
@Disabled("run on demand")
class RentalStoreIntegrityTest {

    @Autowired
    private RentalFacade rentals;

    @Container
    private static PostgreSQLContainer psql = new PostgreSQLContainer(DockerImageName.parse("postgres:15.3"))
      .withDatabaseName("foo")
      .withUsername("foo")
      .withPassword("secret");

    @Test
    void shouldNotThrowOnConcurrentWrite() throws Exception {
        var movieId = new MovieId(42);
        var movieId2 = new MovieId(43);

        rentals.addMovie(new MovieAddRequest(movieId.id(), "Spiderman", "NEW"));
        rentals.addMovie(new MovieAddRequest(movieId2.id(), "Spiderman 2", "NEW"));

        var e = Executors.newFixedThreadPool(2);
        var first = CompletableFuture.runAsync(() -> {
            rentals.rentMovie("gpiwowarek@gmail.com", movieId);
        }, e);

        CompletableFuture.runAsync(() -> {
            rentals.rentMovie("gpiwowarek@gmail.com", movieId2);
        }, e);

        first.join();
    }

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", psql::getJdbcUrl);
        registry.add("spring.datasource.username", psql::getUsername);
        registry.add("spring.datasource.password", psql::getPassword);
    }

    @TestConfiguration
    static class TestDelayConfig {

        @Primary
        @Bean
        public RentalHistoryRepository jdbcRentalHistoryRepository(JdbcTemplate jdbcTemplate) {
            return new DelayingRentalRepository(new JdbcRentalHistoryRepository(jdbcTemplate));
        }
    }

    // alternative approach to delay injection
    static class DelayingRentalRepository implements RentalHistoryRepository {

        private final RentalHistoryRepository rentalHistoryRepository;

        private final AtomicInteger seq = new AtomicInteger();

        DelayingRentalRepository(RentalHistoryRepository rentalHistoryRepository) {
            this.rentalHistoryRepository = rentalHistoryRepository;
        }

        @Override
        public void saveRentEvent(MovieId id, String login, long eventId) {
            delay();
            rentalHistoryRepository.saveRentEvent(id, login, eventId);
        }

        @Override
        public void saveReturnEvent(MovieId id, String login, long eventId) {
            delay();
            rentalHistoryRepository.saveReturnEvent(id, login, eventId);
        }

        @Override
        public long lastEventId() {
            return rentalHistoryRepository.lastEventId();
        }

        @Override
        public List<MovieRentalEvent> findAll() {
            return rentalHistoryRepository.findAll();
        }

        @Override
        public List<MovieRentalEvent> findUserRentals(String login) {
            return rentalHistoryRepository.findUserRentals(login);
        }

        @Override
        public List<MovieRentalEvent> findMovieRentals(MovieId movieId) {
            return rentalHistoryRepository.findMovieRentals(movieId);
        }

        @Override
        public List<MovieRentalEvent> getUnprocessed() {
            return rentalHistoryRepository.getUnprocessed();
        }

        @Override
        public void markProcessed(MovieRentalEvent event) {
            rentalHistoryRepository.markProcessed(event);
        }

        private void delay() {
            if (seq.getAndIncrement() % 2 == 0) {
                try {
                    System.out.println("Delaying for 5000ms...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    System.out.println("Delaying for 100ms...");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
