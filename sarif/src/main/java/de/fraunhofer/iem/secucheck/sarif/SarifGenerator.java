package de.fraunhofer.iem.secucheck.sarif;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fraunhofer.iem.secucheck.analysis.datastructures.DifferentTypedPair;
import de.fraunhofer.iem.secucheck.analysis.query.SecucheckTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.query.TaintFlowImpl;
import de.fraunhofer.iem.secucheck.analysis.result.*;
import de.fraunhofer.iem.secucheck.commons.ApplicationPropertiesUtility;
import de.fraunhofer.iem.secucheck.commons.FluentTQLUtility;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.english.BriefFluentTQL2Eng;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.sarif.runs.Runs;
import de.fraunhofer.iem.secucheck.sarif.runs.result.Result;
import de.fraunhofer.iem.secucheck.sarif.runs.result.location.Location;
import de.fraunhofer.iem.secucheck.sarif.runs.result.location.physicalLocation.PhysicalLocation;
import de.fraunhofer.iem.secucheck.sarif.runs.result.location.physicalLocation.fileLocation.ArtifactLocation;
import de.fraunhofer.iem.secucheck.sarif.runs.result.location.region.Region;
import de.fraunhofer.iem.secucheck.sarif.runs.result.message.Message;
import de.fraunhofer.iem.secucheck.sarif.runs.tool.Driver;
import de.fraunhofer.iem.secucheck.sarif.runs.tool.Tool;
import de.fraunhofer.iem.secucheck.sarif.runs.Properties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates the SARIF as a Json string
 *
 * @author Ranjith Krishnamurthy
 */
public class SarifGenerator {
    private static SecucheckTaintAnalysisResult secucheckTaintAnalysisResult;
    private static List<TaintFlowQuery> taintFlowQueries;
    private static String baseDir;

    /**
     * Generates the SARIF in json format as a string.
     *
     * @param secucheckTaintAnalysisResult SecuCheck core analysis result
     * @param taintFlowQueries             List of TaintFlowQueries used for the analysis
     * @param baseDir                      Class path directory, where the secucheck-cmd analyzed the classes
     * @return SARIF as Json string
     * @throws JsonProcessingException If fails to convert into Json string
     */
    public static String getSarifAsJsonString(SecucheckTaintAnalysisResult secucheckTaintAnalysisResult, List<TaintFlowQuery> taintFlowQueries, String baseDir) throws JsonProcessingException {
        SarifGenerator.secucheckTaintAnalysisResult = secucheckTaintAnalysisResult;
        SarifGenerator.taintFlowQueries = taintFlowQueries;
        SarifGenerator.baseDir = baseDir;

        Sarif sarif = getSarif();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sarif);
    }

    /**
     * Returns the SARIF object
     *
     * @return SARIF
     */
    private static Sarif getSarif() {
        Sarif sarif = new Sarif();

        sarif.setVersion("2.1.0");
        sarif.set$schema("http://json.schemastore.org/sarif-2.1.0");

        List<Runs> runs = new ArrayList<>();
        runs.add(getRuns());

        sarif.setRuns(runs);

        return sarif;
    }

    /**
     * Returns the Run object
     *
     * @return Run
     */
    private static Runs getRuns() {
        Runs runs = new Runs();

        Tool tool = getTool();
        runs.setTool(tool);
        runs.setLanguage(ApplicationPropertiesUtility.getLanguage());

        ArrayList<Result> results = new ArrayList<>();

        for (DifferentTypedPair<SecucheckTaintFlowQueryImpl, SecucheckTaintFlowQueryResult> result : secucheckTaintAnalysisResult.getResults()) {
            results.addAll(getResult(result));
        }

        runs.setResults(results);

        Properties properties = new Properties();
        properties.setCopyright(ApplicationPropertiesUtility.getCopyright());

        runs.setProperties(properties);

        return runs;
    }

    /**
     * Returns the List of Result object
     *
     * @param result Secucheck TaintFlowQuery result
     * @return Result
     */
    private static List<Result> getResult(DifferentTypedPair<SecucheckTaintFlowQueryImpl, SecucheckTaintFlowQueryResult> result) {
        List<Result> results = new ArrayList<>();

        TaintFlowQuery taintFlowQuery = FluentTQLUtility.getTaintFlowQueryFromID(taintFlowQueries, result.getFirst().getId());

        String briefFluentTQL2Eng = "";
        String fluentTQL2English = "";

        if (taintFlowQuery != null) {
            briefFluentTQL2Eng = new BriefFluentTQL2Eng().translate(taintFlowQuery);
            fluentTQL2English = new FluentTQL2English().translate(taintFlowQuery);
        }

        // This loop is for and operator. If only one then no and operator.
        for (DifferentTypedPair<TaintFlowImpl, TaintFlowResult> taintFlowResultWithAndOp : result.getSecond().getResults()) {
            // This loop is for the single taintflow there are multiple instance are found in the source.
            for (DifferentTypedPair<TaintFlowImpl, SingleTaintFlowAnalysisResult> taintFlowResult : taintFlowResultWithAndOp.getSecond().getQueryResultMap()) {
                Result res = new Result();

                res.setRuleId(taintFlowQuery.getId());
                res.setRuleMessageId(taintFlowQuery.getReportLocation() + " : \n" + briefFluentTQL2Eng);
                res.setRichMessageId(taintFlowQuery.getReportLocation() + " : \n" + fluentTQL2English);

                Message message = new Message();
                message.setText(taintFlowQuery.getReportMessage());
                res.setMessage(message);

                res.setLevel("error");

                List<Location> locations = new ArrayList<>();

                if (taintFlowQuery.getReportLocation() == LOCATION.SOURCE || taintFlowQuery.getReportLocation() == LOCATION.SOURCEANDSINK) {
                    Location location = new Location();

                    PhysicalLocation physicalLocation = new PhysicalLocation();
                    FileLocation fileLocation = new FileLocation();

                    LocationDetails sourceDetails = taintFlowResult.getSecond().getLocationDetails().getSecond().getFirst();

                    String fqn = baseDir +
                            File.separator +
                            sourceDetails.getUsageClassName().replace(
                                    ".",
                                    File.separator
                            ) +
                            ".class";

                    File file = new File(fqn);

                    if (file.exists()) {
                        fileLocation.setUri(file.getAbsolutePath());
                    } else {
                        fileLocation.setUri(sourceDetails.getUsageClassName().replace(".", File.separator) + ".class");
                    }

                    physicalLocation.setFileLocation(fileLocation);
                    location.setPhysicalLocation(physicalLocation);

                    Region region = new Region();

                    region.setStartColumn(sourceDetails.getUsageStartColumnNumber());
                    region.setEndColumn(sourceDetails.getUsageEndColumnNumber());
                    region.setStartLine(sourceDetails.getUsageStartLineNumber());
                    region.setEndLine(sourceDetails.getUsageEndLineNumber());

                    location.setRegion(region);
                    locations.add(location);
                }

                if (taintFlowQuery.getReportLocation() == LOCATION.SINK || taintFlowQuery.getReportLocation() == LOCATION.SOURCEANDSINK) {
                    Location location = new Location();

                    PhysicalLocation physicalLocation = new PhysicalLocation();
                    FileLocation fileLocation = new FileLocation();

                    LocationDetails sinDetails = taintFlowResult.getSecond().getLocationDetails().getSecond().getSecond();

                    String fqn = baseDir +
                            File.separator +
                            sinDetails.getUsageClassName().replace(
                                    ".",
                                    File.separator
                            ) +
                            ".class";

                    File file = new File(fqn);

                    if (file.exists()) {
                        fileLocation.setUri(file.getAbsolutePath());
                    } else {
                        fileLocation.setUri(sinDetails.getUsageClassName().replace(".", File.separator) + ".class");
                    }

                    physicalLocation.setFileLocation(fileLocation);
                    location.setPhysicalLocation(physicalLocation);

                    Region region = new Region();

                    region.setStartColumn(sinDetails.getUsageStartColumnNumber());
                    region.setEndColumn(sinDetails.getUsageEndColumnNumber());
                    region.setStartLine(sinDetails.getUsageStartLineNumber());
                    region.setEndLine(sinDetails.getUsageEndLineNumber());

                    location.setRegion(region);
                    locations.add(location);
                }

                res.setLocations(locations);

                results.add(res);
            }
        }
        return results;
    }

    /**
     * Returns the Tool object
     *
     * @return Tool
     */
    private static Tool getTool() {
        Tool tool = new Tool();
        ApplicationPropertiesUtility.loadApplicationProperties();

        tool.setName(ApplicationPropertiesUtility.getName());
        tool.setFullName(ApplicationPropertiesUtility.getFullName());
        tool.setVersion(ApplicationPropertiesUtility.getVersion());
        tool.setSemanticVersion(ApplicationPropertiesUtility.getSemanticVersion());
        tool.setLanguage(ApplicationPropertiesUtility.getLanguage());

        ToolProperties properties = new ToolProperties();
        properties.setCopyright(ApplicationPropertiesUtility.getCopyright());

        tool.setProperties(properties);

        return tool;
    }
}
