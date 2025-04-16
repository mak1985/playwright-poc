package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;

    // Element Locators
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator submitButton;
    private final Locator congratsText;

    // Constructor
    public LoginPage(Page page) {
        this.page = page;
        this.usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username"));
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.congratsText = page.getByRole(AriaRole.PARAGRAPH)
                .filter(new Locator.FilterOptions().setHasText("Congratulations student. You"));
    }

    /**
     * Enter username into the username input field.
     * @param username The username to enter.
     */
    public void enterUsername(String username) {
        usernameInput.fill(username, new Locator.FillOptions().setTimeout(5000));
    }

    /**
     * Enter password into the password input field.
     * @param password The password to enter.
     */
    public void enterPassword(String password) {
        passwordInput.fill(password, new Locator.FillOptions().setTimeout(10000));
    }

    /**
     * Click the submit button.
     */
    public void clickSubmit() {
        submitButton.click();
    }

    /**
     * Click the congratulatory text element.
     */
    public void clickCongratsText() {
        congratsText.click();
    }

    /**
     * Complete login flow: username → password → submit → confirm.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmit();
        clickCongratsText();
    }
}
