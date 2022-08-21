package io.cucumber.examples.spring.application;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivelinessStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(LivelinessStepDefinitions.class);
    private static Response response;
    private static final String BASE_URL = "http://10.141.159.158";

    @Given("the liveliness endpoint is called")
    public void theLivelinessEndpointIsCalled() {
        System.out.println("==> liveliness");
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = 4000;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get("/debug/liveness");
        assertEquals(200, response.getStatusCode());
    }

    @Then("the liveliness response is positive")
    public void theServiceResponseIsPositive() {
        // String jsonString = response.asPrettyString();
        // log.info("Pretty: {}", jsonString);
        // String status = JsonPath.from(jsonString).get("status");
        String status = response.jsonPath().getString("status");
        log.info("Status: {}", status);
        assertEquals("up", status);
    }
}
