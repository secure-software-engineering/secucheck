package de.fraunhofer.iem.secucheck.cmd;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Main class of the SecuCheck-cmd
 *
 * @author Ranjith Krishnamurthy
 */
public class Main {
    private static final String SECU_CONFIG_PATH_SHORT = "-scp";
    private static final String OUT_DIR_SHORT = "-o";

    private static final String HELP_MESSAGE = "Invalid option. Available option is:\n" +
            SECU_CONFIG_PATH_SHORT + " <SecuCheck configuration file path>\n" +
            OUT_DIR_SHORT + " <Analysis result out-directory>\n";

    private static String secuCheckConfigurationFilePath = "";
    private static String analysisResultOutDIrectory = "";

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Check for the invalid arguments
        if (!checkArguments(args)) {
            System.out.println(HELP_MESSAGE);
            return;
        }

        // Check for the valid SecuCheckConfiguration file
        File file = new File(secuCheckConfigurationFilePath);

        if (!file.exists() || !secuCheckConfigurationFilePath.endsWith(".yml")) {
            System.err.println("Given SecuCheck Configuration file(" + secuCheckConfigurationFilePath + ") does not exist or invalid!!");
        }

        //TODO: Verify the out-dir

        // Load the SecuCheck configuration yaml file
        SecuCheckConfiguration secuCheckConfiguration = YamlUtils.loadYamlAndGetSecuCheckConfiguration(secuCheckConfigurationFilePath);

        //TODO: Start processing the specification and call the SecuCheck analysis
        System.out.println(secuCheckConfiguration.getClassPath());
        for (String ep : secuCheckConfiguration.getEntryPoints()) {
            System.out.println(ep);
        }
        System.out.println(secuCheckConfiguration.getSpecPath());
        for (String ss : secuCheckConfiguration.getSelectedSpecs()) {
            System.out.println(ss);
        }
        System.out.println(secuCheckConfiguration.getSolver());
    }

    /**
     * Checks whether the given command line arguments is correct or not
     *
     * @param args Command line arguments
     * @return True if the command line arguments are valid otherwise false
     */
    private static boolean checkArguments(String[] args) {
        // Given argument cound should be exact 4
        if (args.length < 4) {
            System.err.println("Got too few arguments.");
            return false;
        }

        // Given argument cound should be exact 4
        if (args.length > 4) {
            System.err.println("Got too many arguments.");
            return false;
        }

        // If two options are same then false
        if (args[0].equals(args[2]))
            return false;

        // Check whether the second option is a valid option
        if (!(SECU_CONFIG_PATH_SHORT.equals(args[2]) || OUT_DIR_SHORT.equals(args[2])))
            return false;

        // Check whether the first option is a valid option
        if (!(SECU_CONFIG_PATH_SHORT.equals(args[0]) || OUT_DIR_SHORT.equals(args[0])))
            return false;

        // Take the first option value and store it in a correct register
        if (SECU_CONFIG_PATH_SHORT.equals(args[0]))
            secuCheckConfigurationFilePath = args[1];
        else
            analysisResultOutDIrectory = args[1];

        // Take the second option value and store it in a correct register
        if (OUT_DIR_SHORT.equals(args[2]))
            analysisResultOutDIrectory = args[3];
        else
            secuCheckConfigurationFilePath = args[3];

        return true;
    }
}
