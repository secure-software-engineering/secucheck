package de.fraunhofer.iem.secucheck;

import java.io.IOException;
import java.util.Properties;

/**
 * Application properties Utility of complete project. Application properties file needs to be placed in the
 * de.fraunhofer.iem.secucheck.secucheck-commons-utility maven module|S resource directory
 *
 * @author Ranjith Krishnamurthy
 */
public class ApplicationPropertiesUtility {
    private static Properties properties;

    /**
     * Loads the application.properties
     */
    public static void loadApplicationProperties() {
        properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException ignored) {
            properties = null;
        }
    }

    public static String getVersion() {
        return properties != null ? properties.getProperty("version") : "";
    }

    public static String getSemanticVersion() {
        return properties != null ? properties.getProperty("semanticVersion") : "";
    }

    public static String getName() {
        return properties != null ? properties.getProperty("name") : "";
    }

    public static String getFullName() {
        return properties != null ? properties.getProperty("fullName") : "";
    }

    public static String getLanguage() {
        return properties != null ? properties.getProperty("language") : "";
    }

    public static String getCopyright() {
        return properties != null ? properties.getProperty("copyright") : "";
    }
}
