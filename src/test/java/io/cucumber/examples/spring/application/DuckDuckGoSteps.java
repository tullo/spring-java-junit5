package io.cucumber.examples.spring.application;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DuckDuckGoSteps {

    // https://automationrhapsody.com/introduction-to-cucumber-and-bdd-with-examples/
    // https://automationtestings.com/element-not-interactable-exception-selenium/

    private final Logger log = LoggerFactory.getLogger(DuckDuckGoSteps.class);
    private WebDriver driver;

    @Before
    public void createDriver() {
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.get("https://duckduckgo.com/");
        driver.navigate().to("https://duckduckgo.com/");

        String title = driver.getTitle();
        assertTrue(title.contains("DuckDuckGo — Privacy, simplified."));
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Given("Enter search term {string}")
    public void searchFor(String searchTerm) {
        log.info("Given: Enter search term '{}'", searchTerm);
        WebElement searchField = driver.findElement(By.name("q"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(searchField));
        wait.until(ExpectedConditions.elementToBeClickable(searchField));

        searchField.sendKeys(searchTerm);
        //Thread.sleep(2000);

        String value = searchField.getAttribute("value");
        assertEquals("Cucumber", value);
    }

    @When("Do search")
    public void clickSearchButton() {
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.submit();
    }

    @Then("Then Single result is shown for {string}")
    public void assertSingleResult(String searchTerm) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> links = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("links")));
        for (WebElement l : links) {
            System.out.println(l.getText().contains(searchTerm));
            assertTrue(l.getText().contains(searchTerm));
        }
    }
}
