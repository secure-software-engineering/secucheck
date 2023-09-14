package de.fraunhofer.iem.secucheck.sarif.runs.result.location.physicalLocation.fileLocation;

/**
 * File location item in the SARIF
 *
 * @author Ranjith Krishnamurthy
 */
public class FileLocation {
    /**
     * Uri to the file: In secucheck-cmd its the class file
     */
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
