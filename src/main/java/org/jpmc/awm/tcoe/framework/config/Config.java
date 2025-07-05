package org.jpmc.awm.tcoe.framework.config;


import java.io.InputStream;
import java.util.Properties;

public class Config {

// This class is used to load configuration properties from a file named config.properties
// It provides methods to retrieve properties by key, with optional default values

    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) throw new RuntimeException("config.properties not found");
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error loading config", e);
        }
    }

    public static String get(String key) {
        // Give priority to System properties (like -Denv=staging)
        return System.getProperty(key, props.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }

    public static String getEnvUrl() {
        String env = get("env", "pba").toLowerCase();
        return get(env + ".url");
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

}
