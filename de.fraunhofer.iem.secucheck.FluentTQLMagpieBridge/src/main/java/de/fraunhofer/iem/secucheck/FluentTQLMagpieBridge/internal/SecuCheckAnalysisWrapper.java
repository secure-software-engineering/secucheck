package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.SecuCheckCoreQueryUtility;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysis;
import de.fraunhofer.iem.secucheck.analysis.SecucheckTaintAnalysis;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.datastructures.DifferentTypedPair;
import de.fraunhofer.iem.secucheck.analysis.query.SecucheckTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.result.AnalysisResultListener;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowResult;
import magpiebridge.core.AnalysisResult;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public final class SecuCheckAnalysisWrapper implements SecucheckMagpieBridgeAnalysis {

    private boolean isCancelled;
    private SecucheckAnalysis analysis;

    public SecuCheckAnalysisWrapper(boolean inProc, SecucheckAnalysisConfiguration configuration) {
        //analysis = inProc ? new SecucheckTaintAnalysis() : new SecuCheckTaintAnalysisOutOfProcess();
        analysis = new SecucheckTaintAnalysis();
        analysis.setConfiguration(configuration);
    }

    public void isCancelled(boolean value) {
        this.isCancelled = value;
    }

    public Collection<AnalysisResult> run(List<TaintFlowQuery> configTaintFlows,
                                          List<String> analysisFiles, Set<Path> userClassPaths, Set<Path> refferedClassPaths,
                                          String projectPath) throws Exception {
    /*    analysis.setOs(Utility.getOperatingSystem());
        analysis.setAnalysisEntryPoints(Utility.getAllMethodsEntryPoints(analysisFiles));
        analysis.setApplicationClassPath(Utility.getAppendedUserClassPath(userClassPaths, refferedClassPaths));
        analysis.setSootClassPathJars(Utility.getSootClassPath());
        analysis.setListener(getResultListener());
*/
        DifferentTypedPair<HashMap<Integer, TaintFlowQuery>, List<SecucheckTaintFlowQueryImpl>> queriesWithID = SecuCheckCoreQueryUtility.getCompositeTaintFlowQueries(configTaintFlows);
        SecucheckTaintAnalysisResult result = analysis.run(queriesWithID.getSecond());

        return Utility.getMagpieBridgeResult(result, queriesWithID.getFirst());
    }

    private AnalysisResultListener getResultListener() {
        return new AnalysisResultListener() {
            public void reportCompleteResult(SecucheckTaintAnalysisResult arg0) {
            }

            @Override
            public void reportSecucheckTaintFlowQueryResult(SecucheckTaintFlowQueryResult secucheckTaintFlowQueryResult) {

            }

            @Override
            public void reportTaintFlowResult(TaintFlowResult taintFlowResult) {

            }

            public boolean isCancelled() {
                return isCancelled;
            }
        };
    }
}
