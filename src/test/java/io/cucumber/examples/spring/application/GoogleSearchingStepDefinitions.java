package io.cucumber.examples.spring.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.en.*;

public class GoogleSearchingStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(GoogleSearchingStepDefinitions.class);

    @Given("a web browser is on the Google page")
    public void aWebBrowserIsOnTheGooglePage() {
        log.info("Given: a web browser is on the Google page");
    }

    @When("the search phrase {string} is entered")
    public void theSearchPhraseIsEntered(String phrase) {
        log.info("When: the search phrase '{}' is entered", phrase);
    }

    @Then("results for {string} are shown")
    public void resultsForAreShown(String phrase) {
        log.info("Then: results for '{}' are shown", phrase);
    }

    @And("the related results include {string}")
    public void theRelatedResultsInclude(String phrase) {
        log.info("And: the related results include '{}'", phrase);
    }

    @But("the related results do not include {string}")
    public void theRelatedResultsDoNotInclude(String phrase) {
        log.info("But: the related results do not include '{}'", phrase);
    }
}
