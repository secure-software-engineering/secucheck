package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import magpiebridge.core.AnalysisResult;

public interface SecucheckMagpieBridgeAnalysis {

	Collection<AnalysisResult> run(List<TaintFlowQuery> configTaintFlows, 
			List<String> analysisFiles, Set<Path> userClassPaths, Set<Path> refferedClassPaths, 
			String projectPath) throws Exception;
}
