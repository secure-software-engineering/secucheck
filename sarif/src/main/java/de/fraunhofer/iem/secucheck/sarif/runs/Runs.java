package de.fraunhofer.iem.secucheck.sarif.runs;

import de.fraunhofer.iem.secucheck.sarif.runs.result.Result;
import de.fraunhofer.iem.secucheck.sarif.runs.tool.Tool;

import java.util.List;

/**
 * Run item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Runs {
    /**
     * Tool item
     */
    private Tool tool;

    /**
     * Language, always en
     */
    private String language;

    /**
     * Results items. It contains multiple items for each taintflow found by the analysis
     */
    private List<Result> results;

    /**
     * Tool properties that contains the copyright
     */
    private Properties properties;

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
