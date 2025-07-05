package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;



public class ElementHelper {
    private final Page page;

    public ElementHelper(Page page) {
        this.page = page;
    }

    public Locator waitForVisible(String selector, int timeoutMs) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions()
                .setTimeout(timeoutMs)
                .setState(WaitForSelectorState.VISIBLE));
        return locator;
    }

    public void clickWhenReady(String selector, int timeoutMs) {
        waitForVisible(selector, timeoutMs).click();
    }

    public void fillInput(String selector, String text) {
        page.locator(selector).fill(text);
    }

    public boolean isElementVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    public String getText(String selector) {
        return page.locator(selector).innerText();
    }

    public void clickHiddenElement(String selector) {
        page.locator(selector).evaluate("element => element.click()", null);
    }
}
