    package org.jpmc.awm.tcoe.framework.utils;


    import com.microsoft.playwright.Page;
    import io.cucumber.java.Scenario;
    import org.jpmc.awm.tcoe.framework.core.TestContext;
    import java.io.File;
    import java.nio.file.Paths;
    import java.text.SimpleDateFormat;
    import java.util.Date;

    public class ScreenshotUtils {
        /**
         * Captures a screenshot of the current page and attaches it to the Cucumber scenario.
         *
         * @param scenario The Cucumber scenario to which the screenshot will be attached.
         * @param title    The title for the screenshot attachment.
         */
        public static void captureScreenshot(Scenario scenario, String title) {
            Page page = TestContext.getPage();
            if (page != null) {
                String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = scenarioName + "_" + timestamp + ".png";
                String screenshotDir = "target/screenshots/";

                // Create directory if it doesn't exist
                new File(screenshotDir).mkdirs();

                String filePath = screenshotDir + fileName;

                byte[] screenshot = page.screenshot(
                        new Page.ScreenshotOptions()
                                .setPath(Paths.get(filePath))
                                .setFullPage(true)
                );

                scenario.attach(screenshot, "image/png", title + " - " + scenarioName);
            }
        }
    }
