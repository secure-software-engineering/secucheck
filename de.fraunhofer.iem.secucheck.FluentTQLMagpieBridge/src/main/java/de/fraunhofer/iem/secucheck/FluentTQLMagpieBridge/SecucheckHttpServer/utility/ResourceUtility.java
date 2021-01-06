package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.utility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ResourceUtility {
    public static String getResourceAsString(String path) {
        try (InputStream inputStream = ResourceUtility.class.getClassLoader().getResourceAsStream(path)) {
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

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
