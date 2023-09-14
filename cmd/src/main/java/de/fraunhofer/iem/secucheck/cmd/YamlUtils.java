package de.fraunhofer.iem.secucheck.cmd;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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
    public static Configuration loadYamlAndGetSecuCheckConfiguration(String secuCheckConfigurationFilePath) throws FileNotFoundException {
        InputStream inputStream = null;

        inputStream = new FileInputStream(secuCheckConfigurationFilePath);

        Yaml yaml = new Yaml(new Constructor(Configuration.class));

        Configuration configuration = null;

        configuration = yaml.load(inputStream);

        return configuration;
    }
}
