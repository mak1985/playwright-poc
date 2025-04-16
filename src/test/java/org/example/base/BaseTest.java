package org.example.base;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class BaseTest {
    protected static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    protected static ThreadLocal<Browser> browser = new ThreadLocal<>();
    protected static ThreadLocal<Page> page = new ThreadLocal<>();
    protected static ThreadLocal<BrowserContext> context = new ThreadLocal<>();


    protected void initializeBrowser() {
        playwright.set(Playwright.create());
        browser.set(playwright.get().chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)));
        context.set(browser.get().newContext());
        page.set(context.get().newPage());

    }

    protected void closeBrowser() {
        if (page.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();
    }


}
