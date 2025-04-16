package org.example.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class Waits {
    // Wait for page to load completely (Network Idle)
    public static void waitForPageLoad(Page page) {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

}
