package com.pivovarit.domain.descriptions;

import com.pivovarit.MovieDescriptionsApplication;
import com.pivovarit.web.DescriptionsController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MovieDescriptionsApplication.class)
@AutoConfigureMessageVerifier
public class ContractTestBase {

    @Autowired
    private MovieDescriptionsFacade facade;

    @Autowired
    private DescriptionsController movieDescriptionsController;

    @BeforeEach
    public void setup() {
        facade.updateDescription(42, "foo");
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(movieDescriptionsController));
    }
}
