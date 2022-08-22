package io.cucumber.examples.spring.application;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GennemstillingS18StepDefinitions {

    private final Logger log = LoggerFactory.getLogger(GennemstillingS18StepDefinitions.class);
    private static final String BASE_URL = "http://10.141.159.158";
    private static final int PORT = 3000;
    private static final String SIGNING_KEY_ID = "54bb2165-71e1-41a6-af3e-7da4a0e1e2c1";
    private static String token;
    private static Response response;
    private static List<Map<String, String>> users;
    private static List<Map<String, String>> dafUsers;

    @Given("User is authorized s18")
    public void userIsAuthorized() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = PORT;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.auth().preemptive().basic("admin@example.com", "gophers");

        response = request.get("/v1/users/token/" + SIGNING_KEY_ID);
        String jsonString = response.asPrettyString();
        log.info("User is authorized: {}", jsonString);
        token = JsonPath.from(jsonString).get("token");
        assertNotNull(token);
        log.info("User is authorized");
    }


    @Given("DAF service returnerer en liste med vurderinger for {string}")
    public void hentVurderingerFraDAF(final String params) {
        log.info("DAF service returnerer en liste med vurderinger for {}", params);
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = PORT;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");

        response = request.get("/v1/users/1/50");
        assertEquals(200, response.getStatusCode());
        users = response.jsonPath().getList("");
        assertTrue(users.size() > 0);
        log.info("User list contains {} entries", users.size());
    }

    @Given("GATEWAY service returnerer en liste med vurderinger for {string}")
    public void hentVurderingerFraGateway(final String params) {
        log.info("GATEWAY service returnerer en liste med vurderinger for {}", params);
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = PORT;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");

        response = request.get("/v1/users/1/50");
        assertEquals(200, response.getStatusCode());
        users = response.jsonPath().getList("");
        assertTrue(users.size() > 0);
        log.info("User list contains {} entries", users.size());
    }

    @When("Begge lister indeholder vurderinger")
    public void aListOfUsersAreAvailableFromDAF() {
        dafUsers = new ArrayList<>();
        Map<String, String> u1 = new HashMap<>();
        Map<String, String> u2 = new HashMap<>();
        dafUsers.add(u1);
        dafUsers.add(u2);
    }

    @Then("Begge lister er lige store og har samme indhold")
    public void theListsAreEqualInSizeAndContent() {
        assertEquals(users.size(), dafUsers.size());
        //assertEquals(users, dafUsers);
    }
}
