package org.jpmc.awm.tcoe.steps;

import org.apache.logging.log4j.Logger;

import io.cucumber.java.en.*;
import org.jpmc.awm.tcoe.framework.config.Config;
import org.jpmc.awm.tcoe.framework.core.TestContext;
import org.jpmc.awm.tcoe.framework.pages.features.LoginPage;
import org.jpmc.awm.tcoe.framework.utils.AssertionHelper;
import org.jpmc.awm.tcoe.framework.utils.LoggerHelper;


public class LoginSteps {
    /**
     * This class contains step definitions for the login feature of the Swag Labs application.
     * It uses Cucumber annotations to define the steps and Playwright for browser interactions.
     */
    private static final Logger log = LoggerHelper.getLogger(LoginSteps.class);
    private LoginPage loginPage;
    private final AssertionHelper assertHelper = new AssertionHelper(log);

    @Given("I am on the Swag Labs login page")
    public void verifyLoginPage() {
        String envUrl = Config.getEnvUrl();
        System.out.println("Navigating to login page: " + envUrl);
        TestContext.navigate(envUrl);
        loginPage = new LoginPage(TestContext.getPage());
        log.info("Navigated to Swag Labs login page: {}", envUrl);
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        log.info("Entering username: {}", username);
        loginPage.enterPassword(password);
        log.info("Entering password: {}", password);
        loginPage.clickLogin();
        log.info("Clicked login button");
    }

    @Then("I should see the result {string}")
    public void i_should_see_the_result(String result) {
        if ("Success".equalsIgnoreCase(result)) {
            assertHelper.assertTrue(
                    loginPage.isOnProductsPage(),
                    "Login successful, redirected to Products page.",
                    "Expected to be on Products page, but wasn't."
            );
        } else {
            String error = loginPage.getError();
            assertHelper.assertContains(
                    error,
                    "epic sadface",
                    "Login failed with expected error.",
                    "Login failed but error message is unexpected."
            );
        }
    }
}
