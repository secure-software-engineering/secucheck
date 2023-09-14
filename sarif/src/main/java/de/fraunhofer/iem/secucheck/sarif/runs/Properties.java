package de.fraunhofer.iem.secucheck.sarif.runs;

/**
 * properties item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class ToolProperties {
    /**
     * Tool copyright
     */
    private String copyright;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
