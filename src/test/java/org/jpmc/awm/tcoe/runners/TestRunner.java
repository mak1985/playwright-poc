package org.jpmc.awm.tcoe.runners;

import org.jpmc.awm.tcoe.framework.utils.BannerPrinter;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")  // This should match where your .feature files are
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.jpmc.awm.tcoe.framework.core,org.jpmc.awm.tcoe.steps,org.jpmc.awm.tcoe.framework.hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty,html:target/cucumber-report.html,com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:target/ExtentReport.html,rerun:target/failed_scenarios.txt"
)
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "")
public class TestRunner {
    // This class remains empty, it is used only as a holder for the above annotations
    // You can add any additional configuration or methods if needed
    static {
        BannerPrinter.printBanner(); // ✅ Prints only once even from IDE run
    }

}
