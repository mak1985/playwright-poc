//package org.jpmc.awm.tcoe.framework.hooks;
//
//
//import io.cucumber.java.After;
//import io.cucumber.java.AfterStep;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.core.LoggerContext;
//import org.jpmc.awm.tcoe.framework.utils.BannerPrinter;
//import org.jpmc.awm.tcoe.framework.utils.LogManagerHelper;
//import org.jpmc.awm.tcoe.framework.utils.ScreenshotUtils;
//
//
//public class Hooks {
//    static {
//        BannerPrinter.printBanner();
//    }
//    public static Logger log;
//
//    @Before
//    public void beforeScenario(Scenario scenario) {
//        BannerPrinter.printBanner();
//        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");
//        log = LogManagerHelper.createScenarioLogger(scenarioName);
//        log.info("===== Starting Scenario: " + scenario.getName() + " =====");
//    }
//    /**
//     * This method is executed after each scenario.
//     * It captures a screenshot if the scenario has failed.
//     *
//     * @param scenario the current scenario
//     */
//    @After
//    public void afterScenario(Scenario scenario) {
//        log.info("Scenario finished: " + scenario.getName() + " - Status: " + scenario.getStatus());
//
//        // Optional: log failure without screenshot (already captured above)
//        if (scenario.isFailed()) {
//            log.error("Scenario Failed: " + scenario.getName());
//        }
//
//        // Clean up logger
//        LoggerContext context = (LoggerContext) LogManager.getContext(false);
//        context.getConfiguration().removeLogger(scenario.getName());
//        context.updateLoggers();
//    }
//
//    @AfterStep
//    public void afterStep(Scenario scenario) {
//        // Capture screenshot only for failed step
//        if (scenario.isFailed()) {
//            ScreenshotUtils.captureScreenshot(scenario, "Step Failed");
//            log.error("Step Failed: " + scenario.getName());
//        }
//    }
//}