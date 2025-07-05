package org.jpmc.awm.tcoe.framework.pages.features;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpmc.awm.tcoe.framework.utils.WaitHelper;

import java.time.Duration;

public class LoginPage {
    /**
     * Represents the login page of the application.
     * Provides methods to interact with the login form and perform login actions.
     */
    private final Page page;
    private final WaitHelper waitHelper;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Locators
    private final String usernameInput = "input[data-test='username'], input[placeholder=Username1]";
    private final String passwordInput = "input[data-test='password1'], input[placeholder=Password]";
    private final String loginButton = "input[data-test='login-button'], button:has-text('Login')";
    private final String errorMessage = "[data-test='error'], .error-message-container, .error-message";

    public LoginPage(Page page) {
        this.page = page;
        this.waitHelper = new WaitHelper(page, logger);
    }

    public void enterUsername(String username) {
        Locator usernameField = page.locator(usernameInput);
        waitHelper.retryType(usernameField, username, Duration.ofSeconds(10), 3);
    }

    public void enterPassword(String password) {
        Locator passwordField = page.locator(passwordInput);
        waitHelper.retryType(passwordField, password, Duration.ofSeconds(10), 3);
    }

    public void clickLogin() {
        Locator loginBtn = page.locator(loginButton);
        waitHelper.retryClick(loginBtn, Duration.ofSeconds(10), 3);
    }

    public String getError() {
        if (page.isVisible(errorMessage)) {
            return page.textContent(errorMessage).trim();
        }
        return "";
    }

    public boolean isOnProductsPage() {
        return page.url().contains("inventory.html");
    }
}
