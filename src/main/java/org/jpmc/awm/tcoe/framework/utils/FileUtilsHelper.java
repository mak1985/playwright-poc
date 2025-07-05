package org.jpmc.awm.tcoe.framework.utils;

import java.io.File;
public class FileUtilsHelper {
    /**
     * Deletes all files inside the "logs" folder before a new test run.
     */
    public static void deleteLogsFolder() {
        File logDir = new File("logs");
        if (logDir.exists() && logDir.isDirectory()) {
            File[] files = logDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        file.delete();
                    }
                }
            }
        } else {
            logDir.mkdirs(); // create folder if missing
        }
    }
}
