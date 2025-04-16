package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.base.BaseTest;

public class Hooks extends BaseTest {

    @Before
    public void setup(Scenario scenario) {
        // Initialize browser per scenario
        super.initializeBrowser();
    }

    @After
    public void tearDown(Scenario scenario) {
        // Capture screenshot on failure
        if (scenario.isFailed()) {
            byte[] screenshot = page.get().screenshot();
            scenario.attach(screenshot, "image/png", "failure_screenshot");
        }
        super.closeBrowser();
    }
}
