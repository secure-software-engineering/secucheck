package de.fraunhofer.iem.secucheck.cmd;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utility file for Yaml
 *
 * @author Ranjith Krishnamurthy
 */
public class YamlUtils {
    /**
     * Loads the Yaml from the given yaml file path and returns the SecuCheckConfiguration
     *
     * @param secuCheckConfigurationFilePath SecuCheck configuration Yaml file path
     * @return loaded SecuCheckConfiguration
     */
    public static SecuCheckConfiguration loadYamlAndGetSecuCheckConfiguration(String secuCheckConfigurationFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(secuCheckConfigurationFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("Given SecuCheck Configuration file(" + secuCheckConfigurationFilePath + ") does not exist or invalid!!");
            return null;
        }

        Yaml yaml = new Yaml(new Constructor(SecuCheckConfiguration.class));

        SecuCheckConfiguration secuCheckConfiguration = null;

        try {
            secuCheckConfiguration = yaml.load(inputStream);
        } catch (Exception exception) {
            System.out.println("Exception = " + exception.getClass().getCanonicalName());
            exception.printStackTrace();
            return null;
        }

        return secuCheckConfiguration;
    }
}
