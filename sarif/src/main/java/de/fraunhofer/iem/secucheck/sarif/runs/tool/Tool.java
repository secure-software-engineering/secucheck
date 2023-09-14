package de.fraunhofer.iem.secucheck.sarif.runs.tool;

/**
 * Tool item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Tool {


    private Driver driver;


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
