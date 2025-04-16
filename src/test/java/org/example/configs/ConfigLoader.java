package org.example.configs;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "src/test/resources/application.yml";  // path to the YAML file

    private static Map<String, Object> config;

    static {
        try {
            Yaml yaml = new Yaml();
            FileReader fileReader = new FileReader(CONFIG_FILE);
            config = yaml.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading configuration file", e);
        }
    }

    public static String getLoginUrl() {
        // Get the URL based on the environment (pba or ipb)
        String environment = System.getProperty("env", "pba");  // Default to "pba" if not specified
        Map<String, Object> environmentConfig = (Map<String, Object>) config.get(environment);
        return (String) ((Map<String, Object>) environmentConfig.get("url")).get("login");
    }

    public static String getBrowser() {
        // Get the browser based on the environment (pba or ipb)
        String environment = System.getProperty("env", "pba");  // Default to "pba" if not specified
        Map<String, Object> environmentConfig = (Map<String, Object>) config.get(environment);
        return (String) environmentConfig.get("browser");
    }
}
