package de.fraunhofer.iem.secucheck.sarif.runs.tool;

public class Driver {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
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
}
