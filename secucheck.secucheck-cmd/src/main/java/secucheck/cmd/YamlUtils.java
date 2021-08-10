package secucheck.cmd;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utility file for Yaml
 *
 */
public class YamlUtils {
    /**
     * Loads the Yaml from the given yaml file path and returns the SecuCheckConfiguration
     *
     * @param secuCheckConfigurationFilePath SecuCheck configuration Yaml file path
     * @return loaded SecuCheckConfiguration
     */
    public static SecuCheckConfiguration loadYamlAndGetSecuCheckConfiguration(String secuCheckConfigurationFilePath) throws FileNotFoundException {
        InputStream inputStream = null;

        inputStream = new FileInputStream(secuCheckConfigurationFilePath);

        Yaml yaml = new Yaml(new Constructor(SecuCheckConfiguration.class));

        SecuCheckConfiguration secuCheckConfiguration = null;

        secuCheckConfiguration = yaml.load(inputStream);

        return secuCheckConfiguration;
    }
}
