package de.fraunhofer.iem.secucheck.cmd;

import com.sun.jna.Platform;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.DuplicateTaintFlowQueryIDException;
import de.fraunhofer.iem.secucheck.analysis.query.OS;
import org.apache.commons.cli.*;

import java.io.File;

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

        SecuCheckConfiguration secuCheckConfiguration = null;

        try {
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

        secuCheckAnalysisConfigurator.run(SecuCheckConfigurationSettingsChecker.getTaintFlowQueries(),
                SecuCheckConfigurationSettingsChecker.getAnalysisSolver(),
                operatingSystem);
    }
}
