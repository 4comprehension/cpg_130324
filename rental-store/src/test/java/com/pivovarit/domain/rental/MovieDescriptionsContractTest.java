package com.pivovarit.domain.rental;

import com.pivovarit.domain.rental.api.MovieId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
  stubsMode = StubRunnerProperties.StubsMode.LOCAL,
  ids = "com.forc.rental:movie-descriptions:+:stubs:8090"
)
@ActiveProfiles("dev")
public class MovieDescriptionsContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplateBuilder restTemplate;

    @Test
    void shouldRetrieveDescriptions() throws Exception {
        HttpMovieDescriptionsRepository client = new HttpMovieDescriptionsRepository(restTemplate.build(), "http://localhost:8090");

        Assertions.assertThat(client.findByMovieId(new MovieId(42))).isNotEmpty();
    }

    @TestConfiguration
    static class HttpDescriptionsConfiguration {
        @Primary
        @Bean
        public HttpMovieDescriptionsRepository movieDescriptionsRepository(RestTemplateBuilder builder) {
            return new HttpMovieDescriptionsRepository(builder.build(), "http://localhost:8090");
        }
    }
}
