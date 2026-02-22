package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties prop;

    static {
        try {
           prop = new Properties();

            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("Config properties not found in classpath");
            }

            prop.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Could not load properties file");
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
