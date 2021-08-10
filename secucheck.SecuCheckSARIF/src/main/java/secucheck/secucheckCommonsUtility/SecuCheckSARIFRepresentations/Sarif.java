package secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations;

import secucheck.secucheckCommonsUtility.SecuCheckSARIFRepresentations.run.Run;

import java.util.List;

/**
 * The top level in the SARIF format
 *
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
    private List<Run> runs;

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

    public List<Run> getRun() {
        return runs;
    }

    public void setRun(List<Run> runs) {
        this.runs = runs;
    }
}
