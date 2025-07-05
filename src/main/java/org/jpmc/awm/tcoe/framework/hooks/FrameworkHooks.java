package org.jpmc.awm.tcoe.framework.hooks;

import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.jpmc.awm.tcoe.framework.core.TestContext;
import org.jpmc.awm.tcoe.framework.utils.*;

public class FrameworkHooks {
    private static boolean isLogCleanupDone = false;
    public static Logger log;

    @BeforeAll
    public static void globalSetup() {
        BannerPrinter.printBanner();
        if (!isLogCleanupDone) {
            FileUtilsHelper.deleteLogsFolder();
            isLogCleanupDone = true;
        }
        TestContext.setup();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        BannerPrinter.printBanner();

        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");
        log = LogManagerHelper.createScenarioLogger(scenarioName);
        log.info("===== Starting Scenario: " + scenario.getName() + " =====");

        TestContext.setup();
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtils.captureScreenshot(scenario, "Scenario Failed");
            log.error("Scenario Failed: " + scenario.getName());
        }

        TestContext.teardown();

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.getConfiguration().removeLogger(scenario.getName());
        context.updateLoggers();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtils.captureScreenshot(scenario, "Step Failed");
            log.error("Step Failed: " + scenario.getName() + " - " + scenario.getStatus());
        }
    }

    @AfterAll
    public static void afterAllTests() {
        ArchiveUtils.archiveTestRunArtifacts();
    }
}
