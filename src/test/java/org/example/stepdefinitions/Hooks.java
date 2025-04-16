package org.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.base.BaseTest;

public class Hooks extends BaseTest {

    @Before
    public void setup(Scenario scenario) {
        System.out.println("🟢 Starting Scenario: " + scenario.getName());
        initializeBrowser();  // ✅ no need for 'super.' since we're already extending
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && page.get() != null) {
            byte[] screenshot = page.get().screenshot();
            scenario.attach(screenshot, "image/png", "failure_screenshot");
        }
        closeBrowser();  // ✅ directly calling inherited method
    }
}
