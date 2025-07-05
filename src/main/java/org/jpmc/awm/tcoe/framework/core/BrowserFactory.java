package org.jpmc.awm.tcoe.framework.core;

import com.microsoft.playwright.*;
import org.jpmc.awm.tcoe.framework.config.Config;

public class BrowserFactory {
    /**
     * Creates a browser instance based on the configuration.
     *
     * @param playwright the Playwright instance
     * @return a Browser instance
     */
    public static Browser createBrowser(Playwright playwright) {
        String browserType = Config.get("browser", "chromium");
        boolean headless = Config.getBoolean("headless");
        int timeout = Config.getInt("browser.timeout");

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setTimeout(timeout);

        switch (browserType.toLowerCase()) {
            case "firefox":
                return playwright.firefox().launch(options);
            case "webkit":
                return playwright.webkit().launch(options);
            default: // chromium
                return playwright.chromium().launch(options);
        }
    }
}
