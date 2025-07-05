package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class WaitHelper {
    private final Page page;
    private final Logger logger;

    public WaitHelper(Page page, Logger logger) {
        this.page = page;
        this.logger = logger;
    }

    public void waitForElementVisible(String selector, Duration timeout) {
        logger.info("Waiting for element to be visible: " + selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout.toMillis()));
    }

    public void waitForElementHidden(String selector, Duration timeout) {
        logger.info("Waiting for element to be hidden: " + selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeout.toMillis()));
    }

    public void waitForElementEnabled(Locator locator, Duration timeout) {
        logger.info("Waiting for element to be enabled.");
        long endTime = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < endTime) {
            if (!locator.isDisabled()) {
                logger.info("Element is enabled.");
                return;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread was interrupted while waiting.", e);
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Timeout waiting for element to be enabled.");
    }

    public void waitForURL(String expectedURL, Duration timeout) {
        logger.info("Waiting for URL to be: " + expectedURL);
        long endTime = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < endTime) {
            if (page.url().equals(expectedURL)) {
                logger.info("URL matched: " + expectedURL);
                return;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread was interrupted while waiting for URL.", e);
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Timeout waiting for URL: " + expectedURL);
    }

    public void retryClick(Locator locator, Duration timeout, int maxRetries) {
        logger.info("Trying to click on element with retry. Max retries: " + maxRetries);
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                locator.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeout.toMillis()));
                locator.click();
                logger.info("Click successful on attempt " + (attempts + 1));
                return;
            } catch (PlaywrightException e) {
                logger.warn("Click failed on attempt " + (attempts + 1) + ": " + e.getMessage());
                attempts++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
            }
        }

        throw new RuntimeException("Failed to click after " + maxRetries + " retries.");
    }

    public void retryType(Locator locator, String text, Duration timeout, int maxRetries) {
        logger.info("Trying to type into element with retry. Max retries: " + maxRetries);
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                locator.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeout.toMillis()));
                locator.fill("");
                locator.type(text);
                logger.info("Typing successful on attempt " + (attempts + 1));
                return;
            } catch (PlaywrightException e) {
                logger.warn("Typing failed on attempt " + (attempts + 1) + ": " + e.getMessage());
                attempts++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
            }
        }

        throw new RuntimeException("Failed to type after " + maxRetries + " retries.");
    }
}
