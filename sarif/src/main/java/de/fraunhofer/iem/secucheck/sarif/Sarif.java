package de.fraunhofer.iem.secucheck.sarif;

import java.util.List;
import de.fraunhofer.iem.secucheck.sarif.runs.Runs;

/**
 * The top level in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Sarif {
    /**
     * SARIF version
     */
    private String version;

    /**
     * SARIF schema
     */
    private String $schema;

    /**
     * Analysis runs, In our case it is always one.
     */
    private List<Runs> runs;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String get$schema() {
        return $schema;
    }

    public void set$schema(String $schema) {
        this.$schema = $schema;
    }

    public List<Runs> getRuns() {
        return runs;
    }

    public void setRuns(List<Runs> runs) {
        this.runs = runs;
    }
}
