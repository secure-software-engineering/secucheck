package de.fraunhofer.iem.secucheck.sarif.runs.tool;

/**
 * Tool item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Tool {
    /**
     * Tool name
     */
    private String name;

    /**
     * Tool full name
     */
    private String fullName;

    /**
     * Tool version
     */
    private String version;

    /**
     * Tool semantic version
     */
    private String semanticVersion;

    /**
     * Language, always en
     */
    private String language;

    private Driver driver;

    public String getName() {
        return name;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSemanticVersion() {
        return semanticVersion;
    }

    public void setSemanticVersion(String semanticVersion) {
        this.semanticVersion = semanticVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ToolProperties getProperties() {
        return properties;
    }

    public void setProperties(ToolProperties properties) {
        this.properties = properties;
    }
}
