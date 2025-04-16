package org.example.stepdefinitions;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.base.BaseTest;
import org.example.configs.ConfigLoader;
import org.example.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class); // SLF4J Logger

    LoginPage loginPage;

    @Given("user is on the login page")
    public void user_is_on_the_login_page() {
        String loginUrl = ConfigLoader.getLoginUrl();
        logger.info("Navigating to the login page: {}", loginUrl); // Log URL

        page.get().navigate(loginUrl, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE)
                .setTimeout(10000));
        loginPage = new LoginPage(page.get());
        logger.info("Login page is successfully loaded.");
    }
    @When("user enters username {string}")
    public void user_enters_username(String username) {
        logger.info("Entering username: {}", username); // Log username
        loginPage.enterUsername(username);

    }
    @When("user enters password {string}")
    public void user_enters_password(String password) {
        loginPage.enterPassword(password);
        logger.info("Entering password for username: {}", password); // Log password (consider masking it for production)
    }
    @When("clicks the submit button")
    public void clicks_the_submit_button() {
        loginPage.clickSubmit();
        logger.info("Clicking the submit button."); // Log submit button click
    }
    @Then("user should see a success message {string}")
    public void user_should_see_a_success_message(String expectedText) {
        logger.info("Verifying success message: {}", expectedText); // Log expected success message
        Locator confirmationText = page.get().getByRole(AriaRole.PARAGRAPH)
                .filter(new Locator.FilterOptions().setHasText(expectedText));

        confirmationText.waitFor();
        logger.info("Success message is displayed.");
    }

}
