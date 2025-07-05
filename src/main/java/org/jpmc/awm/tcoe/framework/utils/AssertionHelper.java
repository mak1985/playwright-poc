package org.jpmc.awm.tcoe.framework.utils;

import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {
    private final Logger logger;

    public AssertionHelper(Logger logger) {
        this.logger = logger;
    }

    public void assertTrue(boolean condition, String successMessage, String failureMessage) {
        try {
            assertThat(condition).isTrue();
            logger.info(successMessage);
        } catch (AssertionError e) {
            logger.error(failureMessage);
            throw e;
        }
    }

    public void assertFalse(boolean condition, String successMessage, String failureMessage) {
        try {
            assertThat(condition).isFalse();
            logger.info(successMessage);
        } catch (AssertionError e) {
            logger.error(failureMessage);
            throw e;
        }
    }

    public void assertEquals(Object actual, Object expected, String successMessage, String failureMessage) {
        try {
            assertThat(actual).isEqualTo(expected);
            logger.info(successMessage);
        } catch (AssertionError e) {
            logger.error(failureMessage + " Actual: " + actual + ", Expected: " + expected);
            throw e;
        }
    }

    public void assertContains(String actual, String expectedSubstring, String successMessage, String failureMessage) {
        try {
            assertThat(actual).containsIgnoringCase(expectedSubstring);
            logger.info(successMessage);
        } catch (AssertionError e) {
            logger.error(failureMessage + " Actual: " + actual + ", Expected to contain: " + expectedSubstring);
            throw e;
        }
    }
}
