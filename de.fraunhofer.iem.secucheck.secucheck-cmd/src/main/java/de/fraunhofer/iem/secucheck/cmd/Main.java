package de.fraunhofer.iem.secucheck.cmd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jna.Platform;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.DuplicateTaintFlowQueryIDException;
import de.fraunhofer.iem.secucheck.SecuCheckSARIFGenerator.SarifGenerator;
import de.fraunhofer.iem.secucheck.analysis.datastructures.DifferentTypedPair;
import de.fraunhofer.iem.secucheck.analysis.query.OS;
import de.fraunhofer.iem.secucheck.analysis.query.SecucheckTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.query.TaintFlowImpl;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.SingleTaintFlowAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowResult;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Main class of the SecuCheck-cmd
 *
 * @author Ranjith Krishnamurthy
 */
public class Main {
    /**
     * Below are the command line arguments options short and long names
     */
    private static final String SECU_CONFIG_PATH_SHORT = "scp";
    private static final String SECU_CONFIG_PATH_LONG = "secu-config-file";
    private static final String OUT_DIR_SHORT = "od";
    private static final String OUT_DIR_LONG = "out-dir";
    private static final String OUT_FILE_SHORT = "of";
    private static final String OUT_FILE_LONG = "out-file";


    private static String analysisResultOutDIrectory = "";

    /**
     * Initializes the command line options.
     * <p>
     * Note: In future, if needed to add new options, then add it here.
     *
     * @return Command line options
     */
    private static Options initializeCommandLineOptions() {
        Options cmdOptions = new Options();

        Option configSettingsFileOption = new Option(
                SECU_CONFIG_PATH_SHORT,
                SECU_CONFIG_PATH_LONG,
                true,
                "SecuCheck configuration settings file");
        configSettingsFileOption.setRequired(true);

        Option outFile = new Option(
                OUT_FILE_SHORT,
                OUT_FILE_LONG,
                true,
                "SecuCheck analysis result output filename without the file extension");
        outFile.setRequired(true);

        Option outDir = new Option(
                OUT_DIR_SHORT,
                OUT_DIR_LONG,
                true,
                "SecuCheck analysis result output directory");
        outFile.setRequired(true);

        cmdOptions.addOption(configSettingsFileOption);
        cmdOptions.addOption(outFile);
        cmdOptions.addOption(outDir);

        return cmdOptions;
    }

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Initialize the command line options
        Options cmdOptions = initializeCommandLineOptions();

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine commandLine;

        try {
            // Parse the command line arguments
            commandLine = commandLineParser.parse(cmdOptions, args);
        } catch (ParseException ex) {
            helpFormatter.printHelp("secucheck-cmd", cmdOptions);
            return;
        }

        String secuCheckConfigurationFilePath = commandLine.getOptionValue(SECU_CONFIG_PATH_LONG);


        // Check for the valid SecuCheckConfiguration file
        File file = new File(secuCheckConfigurationFilePath);

        if (!file.exists() || !secuCheckConfigurationFilePath.endsWith(".yml")) {
            System.err.println("Given SecuCheck Configuration file(" + secuCheckConfigurationFilePath + ") does not exist or invalid!!");
            return;
        }

        // Check for the valid output directory
        File outDirFile = new File(commandLine.getOptionValue(OUT_DIR_LONG));

        if (!outDirFile.exists() || !outDirFile.isDirectory()) {
            System.err.println("Given Output directory (" + commandLine.getOptionValue(OUT_DIR_LONG) +
                    ") is not valid. It is not present or it is not directory.");
            return;
        }

        File outFile = new File(outDirFile.getAbsolutePath() + File.separator + commandLine.getOptionValue(OUT_FILE_LONG) + ".sarif.json");

        if (outFile.exists()) {
            System.err.println("Given filename with the sarif extension (" + commandLine.getOptionValue(OUT_FILE_LONG) +
                    ".sarif.json) already present.");
            return;
        }

        SecuCheckConfiguration secuCheckConfiguration = null;

        try {
            System.out.println("Loading the Yaml settings file.");
            // Load the SecuCheck configuration yaml file
            secuCheckConfiguration = YamlUtils.loadYamlAndGetSecuCheckConfiguration(secuCheckConfigurationFilePath);
        } catch (Exception | Error ex) {
            System.err.println("Could not load the configuration file(" + secuCheckConfigurationFilePath + ").\n" +
                    ex.getMessage());
            return;
        }

        // Check for the valid Secucheck configuration settings
        try {
            SecuCheckConfigurationSettingsChecker.check(secuCheckConfiguration, commandLine.getOptionValue(OUT_DIR_LONG));
        } catch (DuplicateTaintFlowQueryIDException e) {
            System.err.println(e.getMessage());
            return;
        }

        OS operatingSystem;

        if (Platform.isWindows())
            operatingSystem = OS.WINDOWS;
        else if (Platform.isLinux())
            operatingSystem = OS.LINUX;
        else if (Platform.isMac())
            operatingSystem = OS.MACOS;
        else
            operatingSystem = OS.OTHER;

        // Create SecuCheckAnalysisConfigurator and run the analysis
        SecuCheckAnalysisConfigurator secuCheckAnalysisConfigurator = new SecuCheckAnalysisConfigurator(secuCheckConfiguration);

        SecucheckTaintAnalysisResult secucheckTaintAnalysisResult;
        try {
            secucheckTaintAnalysisResult = secuCheckAnalysisConfigurator.run(SecuCheckConfigurationSettingsChecker.getTaintFlowQueries(),
                    SecuCheckConfigurationSettingsChecker.getAnalysisSolver(),
                    operatingSystem);
        } catch (Exception exception) {
            System.err.println("Something went wrong while running analysis:\n" + exception.getMessage());
            exception.printStackTrace();
            return;
        }

        String result = "";

        try {
            result = SarifGenerator.getSarifAsJsonString(secucheckTaintAnalysisResult, SecuCheckConfigurationSettingsChecker.getTaintFlowQueries(), secuCheckConfiguration.getClassPath());
        } catch (JsonProcessingException e) {
            System.err.println("Something went wrong while generating SARIF\n" + e.getMessage());
            return;
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
            bufferedWriter.write(result);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File additionalFile = new File(outDirFile.getAbsolutePath() + File.separator + commandLine.getOptionValue(OUT_FILE_LONG) + "_additional.yml");

        createAdditionalInformationFile(additionalFile,
                secucheckTaintAnalysisResult,
                secuCheckConfiguration);
    }

    private static void createAdditionalInformationFile(File additionalFile,
                                                        SecucheckTaintAnalysisResult secucheckTaintAnalysisResult,
                                                        SecuCheckConfiguration secuCheckConfiguration) {
        if (additionalFile.isDirectory()) {
            try {
                FileUtils.deleteDirectory(additionalFile);
            } catch (IOException ignored) {

            }
        }

        if (additionalFile.exists())
            additionalFile.delete();

        // Main Yaml
        LinkedHashMap<String, Object> root = new LinkedHashMap<>();

        // Start Time
        root.put("startTime", secucheckTaintAnalysisResult.getStartTime());

        // End time
        root.put("endTime", secucheckTaintAnalysisResult.getEndTime());

        // Total time in seconds
        root.put("totalTimeInSec", secucheckTaintAnalysisResult.getExecutionTimeInSec());

        // Total time in milli seconds
        root.put("totalTimeInMilli", secucheckTaintAnalysisResult.getExecutionTimeInMilliSec());

        // Total time in milli seconds
        root.put("totalSeedCount", secucheckTaintAnalysisResult.getTotalSeedCount());

        // Solver
        root.put("solver", secuCheckConfiguration.getSolver());

        ArrayList<LinkedHashMap<String, Object>> taintFlowQueryInfo = new ArrayList<>();

        for (DifferentTypedPair<SecucheckTaintFlowQueryImpl, SecucheckTaintFlowQueryResult> res1 : secucheckTaintAnalysisResult.getResults()) {
            int totalTaintFlowFound = 0;
            int totalSeedCount = 0;
            ArrayList<HashMap<String, Object>> info = new ArrayList<>();

            for (DifferentTypedPair<TaintFlowImpl, TaintFlowResult> res2 : res1.getSecond().getResults()) {
                totalSeedCount += res2.getSecond().getSeedCount();
                totalTaintFlowFound += res2.getSecond().size();
            }

            // Seed count
            HashMap<String, Object> seedCount = new HashMap<String, Object>();
            seedCount.put("seedCount", totalSeedCount);

            info.add(seedCount);

            // TaintFlow found count
            HashMap<String, Object> taintFlowFoundCount = new HashMap<String, Object>();
            taintFlowFoundCount.put("taintFlowFoundCount", totalTaintFlowFound);

            info.add(taintFlowFoundCount);

            taintFlowQueryInfo.add(new LinkedHashMap<String, Object>(){{put(res1.getFirst().getId(), info);}});
        }

        root.put("result", taintFlowQueryInfo);

        // Entry points
        root.put("entryPoints", secuCheckConfiguration.getEntryPoints().iterator());

        // Selected Specs
        root.put("selectedSpecs", secuCheckConfiguration.getSelectedSpecs());

        try {
            PrintWriter printWriter = new PrintWriter(additionalFile);

            Yaml yaml = new Yaml();
            yaml.dump(root, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
