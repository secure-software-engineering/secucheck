package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysis;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.SecucheckTaintAnalysis;
import de.fraunhofer.iem.secucheck.analysis.client.SecuCheckTaintAnalysisOutOfProcess;
import de.fraunhofer.iem.secucheck.analysis.query.CompositeTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.result.AnalysisResultListener;
import de.fraunhofer.iem.secucheck.analysis.result.CompositeTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowQueryResult;
import magpiebridge.core.AnalysisResult;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class SecuCheckAnalysisWrapper implements SecucheckMagpieBridgeAnalysis {

    private boolean isCancelled;
    private SecucheckAnalysis analysis;

    public SecuCheckAnalysisWrapper(boolean inProc, SecucheckAnalysisConfiguration configuration) {
        analysis = inProc ? new SecucheckTaintAnalysis() : new SecuCheckTaintAnalysisOutOfProcess();
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
        List<CompositeTaintFlowQueryImpl> queries = Utility.getCompositeTaintFlowQueries(configTaintFlows);
        SecucheckTaintAnalysisResult result = analysis.run(queries);

        return Utility.getMagpieBridgeResult(result);
    }

    private AnalysisResultListener getResultListener() {
        return new AnalysisResultListener() {
            public void reportFlowResult(TaintFlowQueryResult arg0) {
            }

            public void reportCompositeFlowResult(CompositeTaintFlowQueryResult arg0) {
            }

            public void reportCompleteResult(SecucheckTaintAnalysisResult arg0) {
            }

            public boolean isCancelled() {
                return isCancelled;
            }
        };
    }
}
