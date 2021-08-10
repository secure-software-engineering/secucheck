package secucheck.cmd;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.secucheckCommonsUtility.SecuCheckCoreQueryUtility;
import secucheck.analysis.SecucheckAnalysis;
import secucheck.analysis.SecucheckTaintAnalysis;
import secucheck.analysis.configuration.SecucheckAnalysisConfiguration;
import secucheck.analysis.datastructures.DifferentTypedPair;
import secucheck.analysis.query.SecucheckTaintFlowQueryImpl;
import secucheck.analysis.result.SecucheckTaintAnalysisResult;

import java.util.List;

/**
 * Secuchcek wrapper that wraps the Secucheck analysis and run the analysis
 *
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

        List<SecucheckTaintFlowQueryImpl> compositeTaintFlowQueries = SecuCheckCoreQueryUtility.getCompositeTaintFlowQueries(configTaintFlows);

        return analysis.run(compositeTaintFlowQueries);
    }
}
