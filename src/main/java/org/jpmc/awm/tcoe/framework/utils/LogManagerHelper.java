package org.jpmc.awm.tcoe.framework.utils;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
public class LogManagerHelper {
    public static Logger createScenarioLogger(String scenarioName) {
        String logFile = "logs/" + scenarioName + ".log";

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        // Create layout
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("[%d{HH:mm:ss}] [%t] %-5level %logger{36} - %msg%n")
                .withConfiguration(config)
                .build();

        // Create file appender
        FileAppender fileAppender = FileAppender.newBuilder()
                .withFileName(logFile)
                .withName(scenarioName + "_FileAppender")
                .withLayout(layout)
                .withAppend(false)
                .setConfiguration(config)
                .build();
        fileAppender.start();
        config.addAppender(fileAppender);

        // Create console appender
        ConsoleAppender consoleAppender = ConsoleAppender.newBuilder()
                .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
                .withName(scenarioName + "_ConsoleAppender")
                .withLayout(layout)
                .setConfiguration(config)
                .build();
        consoleAppender.start();
        config.addAppender(consoleAppender);

        // Create appender references
        AppenderRef fileRef = AppenderRef.createAppenderRef(scenarioName + "_FileAppender", Level.INFO, null);
        AppenderRef consoleRef = AppenderRef.createAppenderRef(scenarioName + "_ConsoleAppender", Level.INFO, null);
        AppenderRef[] refs = new AppenderRef[]{fileRef, consoleRef};

        // Create logger config
        LoggerConfig loggerConfig = LoggerConfig.createLogger(
                false, Level.INFO, scenarioName, "true", refs, null, config, null
        );
        loggerConfig.addAppender(fileAppender, Level.INFO, null);
        loggerConfig.addAppender(consoleAppender, Level.INFO, null);

        config.addLogger(scenarioName, loggerConfig);
        context.updateLoggers();

        return LogManager.getLogger(scenarioName);
    }
}
