package de.fraunhofer.iem.secucheck.cmd;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.SecuCheckCoreQueryUtility;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysis;
import de.fraunhofer.iem.secucheck.analysis.SecucheckTaintAnalysis;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.datastructures.DifferentTypedPair;
import de.fraunhofer.iem.secucheck.analysis.query.SecucheckTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;

import java.util.HashMap;
import java.util.List;

/**
 * Secuchcek wrapper that wraps the Secucheck analysis and run the analysis
 *
 * @author Ranjith Krishnamurthy
 */
public class SecuCheckAnalysisWrapper {
    private final SecucheckAnalysis analysis;

    public SecuCheckAnalysisWrapper(SecucheckAnalysisConfiguration configuration) {
        analysis = new SecucheckTaintAnalysis();
        analysis.setConfiguration(configuration);
    }

    /**
     * Converts the given taintflowqueries into Composite taintflow queries that the secuchcek analysis understand. Then run the analysis
     *
     * @param configTaintFlows List Taintflow queries
     * @return Analysis result
     * @throws Exception If fails to run the analysis
     */
    public SecucheckTaintAnalysisResult run(List<TaintFlowQuery> configTaintFlows) throws Exception {

        DifferentTypedPair<HashMap<Integer, TaintFlowQuery>, List<SecucheckTaintFlowQueryImpl>> queriesWithID = SecuCheckCoreQueryUtility.getCompositeTaintFlowQueries(configTaintFlows);

        return analysis.run(queriesWithID.getSecond());
    }
}
