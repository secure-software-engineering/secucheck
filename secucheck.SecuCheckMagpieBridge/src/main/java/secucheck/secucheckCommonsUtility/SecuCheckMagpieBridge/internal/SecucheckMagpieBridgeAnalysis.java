package secucheck.secucheckCommonsUtility.SecuCheckMagpieBridge.internal;

import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import magpiebridge.core.AnalysisResult;

import java.util.Collection;
import java.util.List;

public interface SecucheckMagpieBridgeAnalysis {

    Collection<AnalysisResult> run(List<TaintFlowQuery> taintFlowQueries) throws Exception;
}
