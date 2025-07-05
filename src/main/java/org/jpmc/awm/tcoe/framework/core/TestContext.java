package org.jpmc.awm.tcoe.framework.core;


import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import lombok.Getter; // Using Lombok for cleaner code

@Getter // Lombok automatically creates getter methods
public class TestContext {
    // ThreadLocal variables to hold Playwright, Browser, BrowserContext, and Page instances
    // This ensures that each thread has its own instance, preventing concurrency issues

    private static final ThreadLocal<Playwright> playwright = ThreadLocal.withInitial(Playwright::create);
    private static final ThreadLocal<Browser> browser = ThreadLocal.withInitial(() ->
            BrowserFactory.createBrowser(playwright.get()));
    private static final ThreadLocal<BrowserContext> context = ThreadLocal.withInitial(() ->
            browser.get().newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080)));
    private static final ThreadLocal<Page> page = ThreadLocal.withInitial(() ->
            context.get().newPage());

    public static void setup() {
        // Initialization is done through ThreadLocal.withInitial
        // No need for explicit initialization here
    }

    public static void teardown() {
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }

    public static Page getPage() {
        return page.get();
    }

    public static void navigate(String url) {
        Page page = getPage();
        page.navigate(url);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
