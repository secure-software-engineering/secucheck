package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.SecuCheckCoreQueryUtility;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysis;
import de.fraunhofer.iem.secucheck.analysis.SecucheckTaintAnalysis;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.query.SecucheckTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import magpiebridge.core.AnalysisResult;

import java.util.Collection;
import java.util.List;

/**
 * Wrapper for the SecuCheck core analysis
 *
 * @author Ranjith Krishnamurthy
 */
public final class SecuCheckAnalysisWrapper implements SecucheckMagpieBridgeAnalysis {
    private final SecucheckAnalysis analysis;

    public SecuCheckAnalysisWrapper(SecucheckAnalysisConfiguration configuration) {
        analysis = new SecucheckTaintAnalysis();
        analysis.setConfiguration(configuration);
    }

    /**
     * Generates the composite TaintFlowQueries and calls the SecuCheck core analysis
     *
     * @param taintFlowQueries FluentTQL TaintFlowQueries
     * @return Analysis Result
     * @throws Exception If it fails to run SecuCheck core analysis
     */
    public Collection<AnalysisResult> run(List<TaintFlowQuery> taintFlowQueries) throws Exception {

        List<SecucheckTaintFlowQueryImpl> compositeTaintFlowQueries = SecuCheckCoreQueryUtility.getCompositeTaintFlowQueries(taintFlowQueries);
        SecucheckTaintAnalysisResult result = analysis.run(compositeTaintFlowQueries);

        return Utility.getMagpieBridgeResult(result, taintFlowQueries);
    }
}
