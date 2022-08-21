package io.cucumber.examples.spring.application;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadinessStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(ReadinessStepDefinitions.class);
    private static Response response;
    private static final String BASE_URL = "http://10.141.159.158";

    @Given("the readiness endpoint is called")
    public void theReadinessEndpointIsCalled() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = 4000;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get("/debug/readiness");
        assertEquals(200, response.getStatusCode());
    }

    @Then("the readiness response is positive")
    public void theServiceResponseIsPositive() {
        // log.info("Pretty: {}", response.asPrettyString());
        String status = response.jsonPath().getString("status");
        log.info("Status: {}", status);
        assertEquals("ok", status);
    }
}
