package org.jpmc.awm.tcoe.runners;

import org.junit.platform.suite.api.*;
import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectFile("target/failed_scenarios.txt")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty,html:target/rerun-report.html,com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:target/ExtentRerunReport.html"
)
public class FailedScenariosRunner {

}
