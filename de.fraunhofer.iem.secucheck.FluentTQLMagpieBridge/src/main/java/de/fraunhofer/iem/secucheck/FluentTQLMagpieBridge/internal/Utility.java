package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLAnalysis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import de.fraunhofer.iem.secucheck.analysis.datastructures.DifferentTypedPair;
import de.fraunhofer.iem.secucheck.analysis.datastructures.SameTypedPair;
import de.fraunhofer.iem.secucheck.analysis.query.*;
import de.fraunhofer.iem.secucheck.analysis.result.CompositeTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.LocationDetails;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowQueryResult;
import magpiebridge.core.AnalysisResult;
import magpiebridge.core.Kind;
import magpiebridge.util.SourceCodeReader;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.lsp4j.DiagnosticSeverity;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class Utility {

    private final static OS operatingSystem;
    private final static String pathSeparator;

    static {
        if (SystemUtils.IS_OS_WINDOWS) {
            operatingSystem = OS.Windows;
        } else if (SystemUtils.IS_OS_LINUX) {
            operatingSystem = OS.Linux;
        } else if (SystemUtils.IS_OS_MAC) {
            operatingSystem = OS.MacOS;
        } else {
            operatingSystem = OS.Other;
        }
    }

    static {
        if (SystemUtils.IS_OS_WINDOWS) {
            pathSeparator = ";";
        } else {
            pathSeparator = ":";
        }
    }

    public static OS getOperatingSystem() {
        return operatingSystem;
    }

    public static String getPathSeparator() {
        return pathSeparator;
    }

    public static List<EntryPoint> getAllMethodsEntryPoints(List<String> analysisFiles) {
        List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();
        for (String file : analysisFiles) {
            EntryPoint entryPoint = new EntryPoint();
            entryPoint.setAllMethods(true);
            entryPoint.setCanonicalClassName(file);
            entryPoints.add(entryPoint);
        }
        return entryPoints;
    }

    @SafeVarargs
    public static String getAppendedUserClassPath(Set<Path>... sets) {
        StringBuilder pathBuilder = new StringBuilder();
        for (Set<Path> set : sets) {
            for (Path userClassPath : set) {
                // Scan recursively for all the directories ?
                String path = userClassPath.toAbsolutePath().toString();
                pathBuilder.append(path);
                pathBuilder.append(getPathSeparator());
            }
        }
        return pathBuilder.toString();
    }

    public static String getSootClassPath() {
        return ""; // Will use Soot's Prepend option...
		
		/*
		 * public void setClassPath(MagpieServer server) {
    if (srcPath == null) {
      Optional<IProjectService> opt = server.getProjectService("java");
      if (opt.isPresent()) {
        JavaProjectService ps = (JavaProjectService) server.getProjectService("java").get();
        Set<Path> sourcePath = ps.getSourcePath();
        if (libPath == null) {
          libPath = new HashSet<>();
          ps.getLibraryPath().stream().forEach(path -> libPath.add(path.toString()));
        }
        if (!sourcePath.isEmpty()) {
          Set<String> temp = new HashSet<>();
          sourcePath.stream().forEach(path -> temp.add(path.toString()));
          srcPath = temp;
        }
      }
    }
  }

		 * */
    }

    public static List<CompositeTaintFlowQueryImpl> getCompositeTaintFlowQueries(List<TaintFlowQuery> taintFlowQueries) {
        List<CompositeTaintFlowQueryImpl> compositeQueries = new ArrayList<CompositeTaintFlowQueryImpl>();

        for (TaintFlowQuery flowQuery : taintFlowQueries) {
            CompositeTaintFlowQueryImpl compositeQuery = getCompositeTaintFlowQuery(flowQuery);
            compositeQueries.add(compositeQuery);
        }

        return compositeQueries;
    }

    public static CompositeTaintFlowQueryImpl getCompositeTaintFlowQuery(TaintFlowQuery taintFlowQuery) {
        CompositeTaintFlowQueryImpl compositeQuery = new CompositeTaintFlowQueryImpl();
        compositeQuery.setReportMessage(taintFlowQuery.getReportMessage());
        compositeQuery.setReportLocation(getReportLocation(taintFlowQuery.getReportLocation()));

        for (TaintFlow taintFlow : taintFlowQuery.getTaintFlows()) {
            TaintFlowQueryImpl taintFlowQueryImpl = getTaintFlowQuery(taintFlow);
            compositeQuery.addQuery(taintFlowQueryImpl);
        }

        return compositeQuery;
    }

    private static ReportSite getReportLocation(LOCATION location) {
        switch (location) {
            case SINK:
                return ReportSite.Sink;
            case SOURCE:
                return ReportSite.Source;
            case SOURCEANDSINK:
                return ReportSite.SourceAndSink;
        }
        return ReportSite.Sink;
    }

    private static TaintFlowQueryImpl getTaintFlowQuery(TaintFlow taintFlow) {
        TaintFlowQueryImpl taintFlowQuery = new TaintFlowQueryImpl();

        if (taintFlow.getFrom() != null) {
            for (MethodImpl method : constructMethodsFromParticipant(taintFlow.getFrom())) {
                taintFlowQuery.addFrom(method);
            }
        }

        if (taintFlow.getNotThrough() != null) {
            for (MethodImpl method : constructMethodsFromParticipants(taintFlow.getNotThrough())) {
                taintFlowQuery.addNotThrough(method);
            }
        }

        if (taintFlow.getThrough() != null) {
            for (MethodImpl method : constructMethodsFromParticipants(taintFlow.getThrough())) {
                taintFlowQuery.addThrough(method);
            }
        }

        if (taintFlow.getTo() != null) {
            for (MethodImpl method : constructMethodsFromParticipant(taintFlow.getTo())) {
                taintFlowQuery.addTo(method);
            }
        }

        return taintFlowQuery;
    }

    private static List<MethodImpl> constructMethodsFromParticipants(List<FlowParticipant> participants) {
        List<MethodImpl> methods = new ArrayList<MethodImpl>();

        for (FlowParticipant participant : participants) {
            methods.addAll(constructMethodsFromParticipant(participant));
        }

        return methods;
    }

    private static List<MethodImpl> constructMethodsFromParticipant(FlowParticipant participant) {
        List<Method> methods = new ArrayList<Method>();

        if (participant instanceof Method) {
            methods.add((Method) participant);
        } else if (participant instanceof MethodSet) {
            MethodSet set = (MethodSet) participant;
            methods.addAll(set.getMethods());
        }

        List<MethodImpl> methodImpls = new ArrayList<MethodImpl>();

        for (Method method : methods) {
            methodImpls.add(getMethodImpl(method));
        }

        return methodImpls;
    }

    private static MethodImpl getMethodImpl(Method method) {
        MethodImpl methodImpl = new MethodImpl();
        methodImpl.setName(method.getSignature());
        methodImpl.setSignature(method.getSignature());

        List<InputParameter> inputParams = new ArrayList<InputParameter>();
        if (method.getInputDeclaration() != null &&
                method.getInputDeclaration().getInputs() != null) {
            for (Input input : method.getInputDeclaration().getInputs()) {
                if (input instanceof Parameter) {
                    Parameter parameter = (Parameter) input;
                    InputParameter inputParameter = new InputParameter();
                    inputParameter.setNumber(parameter.getParameterId());
                    inputParams.add(inputParameter);
                } else if (input instanceof ThisObject) {
                    methodImpl.setInputThis(true);
                }
            }
            methodImpl.setInputParameters(inputParams);
        }


        ReturnValue returnValue = null;
        List<OutputParameter> outputParams = new ArrayList<OutputParameter>();

        if (method.getOutputDeclaration() != null &&
                method.getOutputDeclaration().getOutputs() != null) {
            for (Output output : method.getOutputDeclaration().getOutputs()) {
                if (output instanceof Parameter) {
                    Parameter parameter = (Parameter) output;
                    OutputParameter outputParameter = new OutputParameter();
                    outputParameter.setNumber(parameter.getParameterId());
                    outputParams.add(outputParameter);
                } else if (output instanceof ThisObject) {
                    methodImpl.setOutputThis(true);
                } else if (output instanceof Return) {
                    returnValue = new ReturnValue();
                }
            }
            methodImpl.setOutputParameters(outputParams);
            methodImpl.setReturnValue(returnValue);
        }

        return methodImpl;
    }

    public static Collection<AnalysisResult> getMagpieBridgeResult(SecucheckTaintAnalysisResult result)
            throws Exception {
        List<AnalysisResult> results = new ArrayList<AnalysisResult>();
        for (DifferentTypedPair<CompositeTaintFlowQueryImpl,
                CompositeTaintFlowQueryResult> pair : result.getResults()) {
            List<AnalysisResult> magpieBridgeResults = getCompositeResults(pair.getFirst(), pair.getSecond());
            results.addAll(magpieBridgeResults);
        }
        return results;
    }

    private static List<AnalysisResult> getCompositeResults(CompositeTaintFlowQueryImpl query,
                                                            CompositeTaintFlowQueryResult result) throws Exception {
        List<SecuCheckMapieBridgeResult> magpieBridgeResults = new ArrayList<SecuCheckMapieBridgeResult>();

        for (DifferentTypedPair<TaintFlowQueryImpl, TaintFlowQueryResult> pair : result.getResults()) {

            switch (query.getReportLocation()) {
                case Source:
                    magpieBridgeResults.add(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Source));
                    break;
                case Sink:
                    magpieBridgeResults.add(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Sink));
                    break;
                case SourceAndSink:
                    magpieBridgeResults.add(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Source));
                    magpieBridgeResults.add(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Sink));
                    break;
            }
        }
    /*    List<Pair<Position, String>> relatedInfo = new ArrayList<Pair<Position, String>>();
        magpieBridgeResults.forEach(y -> relatedInfo.add(Pair.make(y.position(),
                query.getReportMessage())));
        magpieBridgeResults.forEach(y -> y.setRelated(relatedInfo));*/
        return new ArrayList<AnalysisResult>(magpieBridgeResults);
    }

    private static SecuCheckMapieBridgeResult createMagpieBridgeResult(CompositeTaintFlowQueryImpl compositeQuery,
                                                                       TaintFlowQueryImpl singleFlowQuery, TaintFlowQueryResult singleFlowQueryResult, ReportSite reportLocation)
            throws Exception {
        SecuCheckMapieBridgeResult analysisResult = new SecuCheckMapieBridgeResult();
        if (singleFlowQueryResult.getQueryResultMap().size() == 0) {
            return analysisResult;
        }

        // Start: Hard-coded.
        analysisResult.setKind(Kind.Diagnostic);
        analysisResult.setSeverity(DiagnosticSeverity.Warning);
        // End: Hard-coded.

        analysisResult.setMessage(compositeQuery.getReportMessage());
        DifferentTypedPair<TaintFlowQueryImpl, SameTypedPair<LocationDetails>> pair;
        SameTypedPair<LocationDetails> locationPair;

        switch (reportLocation) {
            default:
            case Source: // First result will have source in all the cases ...
                pair = singleFlowQueryResult.getQueryResultMap().get(0);
                locationPair = pair.getSecond();
                analysisResult.setPosition(createReportPosition(locationPair.getFirst()));
                break;
            case Sink: // Last result will have sink in all the cases ...
                pair = singleFlowQueryResult.getQueryResultMap().get(
                        singleFlowQueryResult.getQueryResultMap().size() - 1);
                locationPair = pair.getSecond();
                analysisResult.setPosition(createReportPosition(locationPair.getSecond()));
                break;
        }

        // Verify if code is needed at all ?
        String code = SourceCodeReader.getLinesInString(analysisResult.position());
        analysisResult.setCode(code);

        // Repair not needed ?
        analysisResult.setRepair(null);

        return analysisResult;
    }

    private static ReportPosition createReportPosition(LocationDetails locationInfo) {
        ReportPosition reportPosition = new ReportPosition();

        System.out.println(locationInfo.getUsageLineNumber());
        // Recheck and debug...
        reportPosition.setFirstLine(locationInfo.getUsageLineNumber());
        reportPosition.setLastLine(locationInfo.getUsageLineNumber());
        reportPosition.setFirstCol(1);
        reportPosition.setLastCol(1);

        for (Path sourcePath : FluentTQLAnalysis.sourcePath) {
            String fqn = sourcePath +
                    File.separator +
                    locationInfo.getUsageClassName().replace(
                            ".",
                            File.separator
                    ) +
                    ".java";

            File file = new File(fqn);

            if (file.exists()) {
                try {
                    reportPosition.setUrl(file.toURI().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Some other source info already available for use
        // sourceLocation.getClassName();
        // sourceLocation.getMethodSignature();
        // sourceLocation.getType();

        // Poisition info still is missing about the source file name,
        // maybe infer it from the class name...

        return reportPosition;
    }
}