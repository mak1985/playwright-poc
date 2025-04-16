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

public class LoginTest extends BaseTest {

    LoginPage loginPage;

    @Given("user is on the login page")
    public void user_is_on_the_login_page() {
        String loginUrl = ConfigLoader.getProperty("url.login");
        page.get().navigate(loginUrl, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE)
                .setTimeout(10000));
        loginPage = new LoginPage(page.get());
    }
    @When("user enters username {string}")
    public void user_enters_username(String username) {
        loginPage.enterUsername(username);
    }
    @When("user enters password {string}")
    public void user_enters_password(String password) {
        loginPage.enterPassword(password);
    }
    @When("clicks the submit button")
    public void clicks_the_submit_button() {
        loginPage.clickSubmit();
    }
    @Then("user should see a success message {string}")
    public void user_should_see_a_success_message(String expectedText) {
        Locator confirmationText = page.get().getByRole(AriaRole.PARAGRAPH)
                .filter(new Locator.FilterOptions().setHasText(expectedText));

        confirmationText.waitFor();
    }

}
