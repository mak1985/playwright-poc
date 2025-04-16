package org.example.base;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.configs.ConfigLoader;

public class BaseTest {
//    protected static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
//    protected static ThreadLocal<Browser> browser = new ThreadLocal<>();
//    protected static ThreadLocal<Page> page = new ThreadLocal<>();
//    protected static ThreadLocal<BrowserContext> context = new ThreadLocal<>();

    public static ThreadLocal<Page> page = new ThreadLocal<>();
    public static ThreadLocal<Browser> browser = new ThreadLocal<>();
    public static ThreadLocal<Playwright> playwright = new ThreadLocal<>();

    public void initializeBrowser() {
        String browserType = ConfigLoader.getBrowser(); // e.g., "firefox", "chromium"

        playwright.set(Playwright.create());
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);

        switch (browserType.toLowerCase()) {
            case "firefox":
                browser.set(playwright.get().firefox().launch(options));
                break;
            case "webkit":
                browser.set(playwright.get().webkit().launch(options));
                break;
            default:
                browser.set(playwright.get().chromium().launch(options));
        }

        BrowserContext context = browser.get().newContext();
        page.set(context.newPage());
    }

    public void closeBrowser() {
        page.get().close();
        browser.get().close();
        playwright.get().close();
    }


}
