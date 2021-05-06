package de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.FluentTQLUtility;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.analysis.datastructures.DifferentTypedPair;
import de.fraunhofer.iem.secucheck.analysis.datastructures.SameTypedPair;
import de.fraunhofer.iem.secucheck.analysis.query.*;
import de.fraunhofer.iem.secucheck.analysis.result.LocationDetails;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowResult;
import de.fraunhofer.iem.secucheck.fluentTQL2English.BriefFluentTQL2Eng;
import de.fraunhofer.iem.secucheck.fluentTQL2English.FluentTQL2English;
import magpiebridge.core.AnalysisResult;
import magpiebridge.core.Kind;
import magpiebridge.util.SourceCodeReader;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.lsp4j.DiagnosticSeverity;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;

public final class Utility {

    private final static OS operatingSystem = null;
    private final static String pathSeparator;

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

    public static Collection<AnalysisResult> getMagpieBridgeResult(SecucheckTaintAnalysisResult result, List<TaintFlowQuery> taintFlowQueries) {
        List<AnalysisResult> results = new ArrayList<AnalysisResult>();

        for (DifferentTypedPair<SecucheckTaintFlowQueryImpl,
                SecucheckTaintFlowQueryResult> pair : result.getResults()) {
            List<AnalysisResult> magpieBridgeResults = getCompositeResults(pair.getFirst(), pair.getSecond(), taintFlowQueries);
            results.addAll(magpieBridgeResults);
        }
        return results;
    }

    private static List<AnalysisResult> getCompositeResults(SecucheckTaintFlowQueryImpl query,
                                                            SecucheckTaintFlowQueryResult result,
                                                            List<TaintFlowQuery> taintFlowQueries) {
        List<SecuCheckMapieBridgeResult> magpieBridgeResults = new ArrayList<SecuCheckMapieBridgeResult>();

        for (DifferentTypedPair<TaintFlowImpl, TaintFlowResult> pair : result.getResults()) {

            switch (query.getReportLocation()) {
                case Source:
                    magpieBridgeResults.addAll(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Source, taintFlowQueries));
                    break;
                case Sink:
                    magpieBridgeResults.addAll(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Sink, taintFlowQueries));
                    break;
                case SourceAndSink:
                    magpieBridgeResults.addAll(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Source, taintFlowQueries));
                    magpieBridgeResults.addAll(createMagpieBridgeResult(query, pair.getFirst(),
                            pair.getSecond(), ReportSite.Sink, taintFlowQueries));
                    break;
            }
        }
    /*    List<Pair<Position, String>> relatedInfo = new ArrayList<Pair<Position, String>>();
        magpieBridgeResults.forEach(y -> relatedInfo.add(Pair.make(y.position(),
                query.getReportMessage())));
        magpieBridgeResults.forEach(y -> y.setRelated(relatedInfo));*/
        return new ArrayList<AnalysisResult>(magpieBridgeResults);
    }

    private static List<SecuCheckMapieBridgeResult> createMagpieBridgeResult(SecucheckTaintFlowQueryImpl compositeQuery,
                                                                             TaintFlowImpl singleFlowQuery,
                                                                             TaintFlowResult singleFlowQueryResult,
                                                                             ReportSite reportLocation,
                                                                             List<TaintFlowQuery> taintFlowQueries) {
        List<SecuCheckMapieBridgeResult> analysisResults = new ArrayList<>();


        if (singleFlowQueryResult.getQueryResultMap().size() == 0) {
            return analysisResults;
        }

        for (DifferentTypedPair<TaintFlowImpl, SameTypedPair<LocationDetails>> pair : singleFlowQueryResult.getQueryResultMap()) {
            SecuCheckMapieBridgeResult analysisResult = new SecuCheckMapieBridgeResult();
            // Start: Hard-coded.
            analysisResult.setKind(Kind.Diagnostic);
            analysisResult.setSeverity(DiagnosticSeverity.Error);
            // End: Hard-coded.

            SameTypedPair<LocationDetails> locationPair;

            String reportMessage = "";

            switch (reportLocation) {
                default:
                case Source: // First result will have source in all the cases ...
//                    pair = singleFlowQueryResult.getQueryResultMap().get(i);
                    locationPair = pair.getSecond();
                    analysisResult.setPosition(createReportPosition(locationPair.getFirst()));
                    reportMessage += "Source: " + compositeQuery.getReportMessage();
                    break;
                case Sink: // Last result will have sink in all the cases ...
                    //           pair = singleFlowQueryResult.getQueryResultMap().get(
                    //                 singleFlowQueryResult.getQueryResultMap().size() - 1);
                    locationPair = pair.getSecond();
                    analysisResult.setPosition(createReportPosition(locationPair.getSecond()));
                    reportMessage += "Sink: " + compositeQuery.getReportMessage();
                    break;
            }

            TaintFlowQuery taintFlowQuery = FluentTQLUtility.getTaintFlowQueryFromID(taintFlowQueries, compositeQuery.getId());

            if (taintFlowQuery != null) {
                reportMessage += "\n\n\n" + new BriefFluentTQL2Eng().translate(taintFlowQuery);
            }

            analysisResult.setMessage(reportMessage);

            //Todo: Verify if code is needed at all ?
            if (analysisResult.position().getFirstCol() < 0 || analysisResult.position().getFirstLine() <= 0) {
                analysisResult.setCode("");
            } else {
                String code = "";
                try {
                    code = SourceCodeReader.getLinesInString(analysisResult.position());
                } catch (Exception | Error ignored) {
                }

                analysisResult.setCode(code);
            }

            // Repair not needed ?
            analysisResult.setRepair(null);

            analysisResults.add(analysisResult);
        }


        return analysisResults;
    }

    private static ReportPosition createReportPosition(LocationDetails locationInfo) {
        ReportPosition reportPosition = new ReportPosition();

        // Recheck and debug...
        reportPosition.setFirstLine(locationInfo.getUsageStartLineNumber());
        reportPosition.setLastLine(locationInfo.getUsageStartLineNumber());
        reportPosition.setFirstCol(1);
        reportPosition.setLastCol(1);

        for (Path sourcePath : FluentTQLAnalysisConfigurator.getSourcePath()) {
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