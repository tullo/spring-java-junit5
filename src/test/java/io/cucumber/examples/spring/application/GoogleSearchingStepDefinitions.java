package io.cucumber.examples.spring.application;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GoogleSearchingStepDefinitions {

    // https://sites.google.com/chromium.org/driver/getting-started?authuser=0
    // https://www.testingdocs.com/cucumber-java-bdd-framework/
    // https://github.com/reportportal/agent-java-cucumber/tree/develop/src/test/resources/features

    private final Logger log = LoggerFactory.getLogger(GoogleSearchingStepDefinitions.class);

    //private final WebDriver driver = new ChromeDriver();

    private WebDriver driver;

    @Before
    public void createDriver() {
        driver = new ChromeDriver();
    }
    @After
    public void quitDriver() {
        driver.quit();
    }

    @Given("a web browser is on the Google page")
    public void aWebBrowserIsOnTheGooglePage() {
        log.info("Given: a web browser is on the Google page");
        driver.get("https://duckduckgo.com/");

        String title = driver.getTitle();
        assertTrue(title.contains("DuckDuckGo"));

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        // throw new PendingException();
    }

    @When("the search phrase {string} is entered")
    public void theSearchPhraseIsEntered(String phrase) {
        log.info("When: the search phrase '{}' is entered", phrase);
        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.id("search_button_homepage"));

        searchBox.sendKeys("Selenium");
        searchButton.click();

        searchBox = driver.findElement(By.name("q"));
        String value = searchBox.getAttribute("value");
        assertEquals("Selenium", value);
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
