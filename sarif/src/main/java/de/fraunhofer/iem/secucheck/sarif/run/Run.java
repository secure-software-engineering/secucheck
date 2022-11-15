package de.fraunhofer.iem.secucheck.SecuCheckSARIFRepresentations.run;

import de.fraunhofer.iem.secucheck.SecuCheckSARIFRepresentations.run.result.Result;
import de.fraunhofer.iem.secucheck.SecuCheckSARIFRepresentations.run.tool.Tool;

import java.util.List;

/**
 * Run item in the SARIF format
 *
 * @author Ranjith Krishnamurthy
 */
public class Run {
    /**
     * Tool item
     */
    private Tool tool;

    /**
     * Results items. It contains multiple items for each taintflow found by the analysis
     */
    private List<Result> results;

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
