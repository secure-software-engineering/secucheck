package de.fraunhofer.iem.secucheck.magpiebridge.internal;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import magpiebridge.core.AnalysisResult;

import java.util.Collection;
import java.util.List;

public interface SecucheckMagpieBridgeAnalysis {

    Collection<AnalysisResult> run(List<TaintFlowQuery> taintFlowQueries) throws Exception;
}
