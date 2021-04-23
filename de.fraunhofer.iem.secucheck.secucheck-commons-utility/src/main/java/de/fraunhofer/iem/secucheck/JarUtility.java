package de.fraunhofer.iem.secucheck;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Utility class for the JarClassLoader
 *
 * @author Ranjith Krishnamurthy
 */
public class JarUtility {
    /**
     * Extract all the jar availabale in the given directory path
     *
     * @param jarFile Directory that contains the jar files
     * @param out     Temporary directoy
     * @return List of jar files
     */
    public static File[] extractJar(File jarFile, File out) {
        if (out.exists())
            deleteDir(out);

        out.mkdir();

        extractJar(jarFile, 0, out.getAbsolutePath());

        File[] files = out.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".jar")) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        return files;
    }

    /**
     * This method recursively extract the Jar files present in the given directory and copies into the outDirectory.
     * This method scans only till the depth level of 20 and scans only 20 sub-directories in the current directory.
     *
     * @param dir       Directory to scan for Jar files
     * @param depth     Current depth of the sub-directories
     * @param outPutDir Out put directory to copy the Jar files
     */
    private static void extractJar(File dir, int depth, String outPutDir) {
        if (depth > 10)
            return;

        FilenameFilter filenameFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".jar")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        File[] files = dir.listFiles(filenameFilter);

        for (File file : files) {
            Path src = Paths.get(file.getAbsolutePath());
            Path dest = Paths.get(outPutDir + System.getProperty("file.separator") + file.getName());
            try {
                if (!dest.toFile().exists())
                    Files.copy(src, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File[] directories = dir.listFiles(File::isDirectory);

        for (int i = 0; i < Objects.requireNonNull(directories).length && i < 21; i++) {
            extractJar(directories[i], depth + 1, outPutDir);
        }
    }

    /**
     * Deletes the given directory and all its content.
     *
     * @param dir File: directory
     */
    public static void deleteDir(File dir) {
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
