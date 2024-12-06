package de.fraunhofer.iem.secucheck;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Utility for resource files
 *
 * @author Ranjith Krishnamurthy
 */
public class ResourceUtility {
    /**
     * Reads the given file name from the resource folder and returns the content as string
     *
     * @param path Path to a file in the resource folder (relative path to resource folder)
     * @return Content of the file in string
     */
    public static String getResourceAsString(String path) {
        try (InputStream inputStream = ResourceUtility.class.getClassLoader().getResourceAsStream(path)) {
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Reads the given file name from the resource folder and returns the content as File
     *
     * @param path Path to a file in the resource folder (relative path to resource folder)
     * @return Content of the file in File
     */
    public static File getResourceAsFile(String path) {
        try (InputStream inputStream = ResourceUtility.class.getClassLoader().getResourceAsStream(path)) {
            File tempFile = new File(
                    Files.createTempFile(
                            "img",
                            path.split("\\.")[path.split("\\.").length - 1]).toUri()
            );
            FileUtils.copyInputStreamToFile(inputStream, tempFile);
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
