package org.example.testrunner;


import org.junit.platform.suite.api.*;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "org.example.stepdefinitions")
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty, html:target/cucumber.html, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
// Parallel execution configuration (Corrected)
@ConfigurationParameter(key = "cucumber.execution.parallel.enabled", value = "true")
@ConfigurationParameter(key = "cucumber.execution.parallel.config.strategy", value = "fixed")  // valid value
@ConfigurationParameter(key = "cucumber.execution.parallel.config.fixed.parallelism", value = "3")  // number of threads
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty,html:target/cucumber-report.html")
@ConfigurationParameter(key = "cucumber.execution.parallel.config.fixed.max-pool-size", value = "3")
//@ConfigurationParameter(key = "cucumber.filter.tags", value = "@smoke")
public class RunCucumberTest {
}
