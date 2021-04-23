package de.fraunhofer.iem.secucheck.cmd;

import java.util.List;

/**
 * Represents the SecuCheckConfiguration. Yaml file should follow this format and vice versa
 *
 * @author Ranjith Krishnamurthy
 */
public class SecuCheckConfiguration {
    /**
     * Path of the classes that SecuCheck analysis should analyze
     */
    private String classPath;

    /**
     * List of entry points for the SecuCheck analysis.
     * If not given, then it takes all the classes avaialble in the classPath as entry points.
     */
    private List<String> entryPoints;

    /**
     * Path of the fluentTQL specifications
     */
    private String specPath;

    /**
     * List of fluentTQL specification, that should be used for the analysis.
     * If not given, then it takes all the fluentTQL specifications for the analysis
     */
    private List<String> selectedSpecs;

    /**
     * SecuCheck analysis solver.
     * <p>
     * Boomerang3
     * FlowDroid
     */
    private String solver;

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public void setEntryPoints(List<String> entryPoints) {
        this.entryPoints = entryPoints;
    }

    public void setSpecPath(String specPath) {
        this.specPath = specPath;
    }

    public void setSelectedSpecs(List<String> selectedSpecs) {
        this.selectedSpecs = selectedSpecs;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public String getClassPath() {
        return classPath;
    }

    public List<String> getEntryPoints() {
        return entryPoints;
    }

    public String getSpecPath() {
        return specPath;
    }

    public List<String> getSelectedSpecs() {
        return selectedSpecs;
    }

    public String getSolver() {
        return solver;
    }
}
