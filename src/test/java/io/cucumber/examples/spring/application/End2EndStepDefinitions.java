package io.cucumber.examples.spring.application;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class End2EndStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(End2EndStepDefinitions.class);

    // https://sites.google.com/chromium.org/driver/getting-started?authuser=0
    // https://www.testingdocs.com/cucumber-java-bdd-framework/
    // https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-assertions-with-junit-5-api/
    // https://github.com/reportportal/agent-java-cucumber/tree/develop/src/test/resources/features

    private static final String USER_ID = "2d6cfe15-208f-4839-a5f8-74f9b7de19ba";
    private static final String USERNAME = "TULLO-Test";
    private static final String PASSWORD = "Test@@123";
    private static final String BASE_URL = "https://bookstore.toolsqa.com";

    private static String token;
    private static Response response;
    private static String jsonString;
    private static String bookId;


    @Given("User is authorized")
    public void userIsAuthorized() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        response = request.body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
                .post("/Account/v1/GenerateToken");

        String jsonString = response.asString();
        token = JsonPath.from(jsonString).get("token");
        log.info("User is authorized: {}", token);

//        request = RestAssured.given();
//        request.header("Content-Type", "application/json");
//        response = request.body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
//                .post("/Account/v1/User");
//        log.info("User created: {}", response.asString());
    }

    @Given("A list of books are available")
    public void listOfBooksAreAvailable() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");

        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        assertTrue(books.size() > 0);

        bookId = books.get(0).get("isbn");
    }

    @When("User adds a book to my reading list")
    public void addBookInList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
        response = request.body("{ \"userId\": \"" + USER_ID + "\", " + "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}").post("/BookStore/v1/Books");
        log.info("When: User adds a book to my reading list: {}", response.asString());
    }

    @Then("The book is added")
    public void bookIsAdded() {
        log.info("Then: The book is added");
        assertEquals(201, response.getStatusCode());
    }

    @When("User removes a book from my reading list")
    public void removeBookFromList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
        response = request.body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + USER_ID + "\"}").delete("/BookStore/v1/Book");

        log.info("When: User removes a book from my reading list: {}", response.asString());
    }

    @Then("The book is removed")
    public void bookIsRemoved() {
        log.info("Then: The book is removed");
        assertEquals(204, response.getStatusCode());

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + token).header("Content-Type", "application/json");
        response = request.get("/Account/v1/User/" + USER_ID);
        assertEquals(200, response.getStatusCode());

        jsonString = response.asString();
        List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
        assertEquals(0, booksOfUser.size());
        log.info("Then: The book is removed - remaining books: {}", booksOfUser.size());
    }
}
