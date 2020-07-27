package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import net.openhft.compiler.CompilerUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to integrate the FluentTQL with the Magpie bridge server.
 *
 * @author Ranjith Krishnamurthy
 */
public class InternalFluentTQLIntegration {
    private static List<String> fileList = new ArrayList<>();
    private static HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    /**
     * This method scans the given path recursively and retrieves all the Java files.
     *
     * @param pathName Path
     */
    private static void getListOfFiles(String pathName) {

        File f = new File(pathName);

        File[] files = f.listFiles();

        for (File file : files) {
            String fileName = file.getName();
            String filePath = file.getAbsolutePath();

            if (file.isDirectory()) {
                getListOfFiles(filePath);
            } else if (fileName.endsWith(".java")) {
                fileList.add(filePath);
            }
        }
    }

    /**
     * This method reads the Java code inside a Java program and then finally it kicks off the
     * compileAndGetTaintFlowQueryObject.
     *
     * @param fileName File name of the Java program.
     * @param filePath Full absolute path of that file.
     * @throws IOException I/O exception while reading the list of files
     */
    private static void readJavaCode(String fileName, String filePath) throws IOException {
        // Reads the Java code as list of String
        List<String> javaCodeAsList = Files.readAllLines(Paths.get(filePath));
        String javaCode = "";
        String className = "";

        for (String line : javaCodeAsList) {
            // This is to get the complete Java code as a single String
            javaCode += line + "\n";

            // This is to get the full qualified package name to concat with the class name to get fully qualified class name.
            if (line.matches("^ *package .*;")) {
                className += line
                        .replace("package", "")
                        .replace(" ", "")
                        .replace(";", "");
            }
        }

        className += "." + fileName.replace(".java", "");

        // This loads the Java code just now we read in the currently running JVM.
        compileAndGetTaintFlowQueryObject(className, javaCode, fileName);
    }

    /**
     * This method loads the Java code in to the currently running JVM and instantiates the object to get the
     * TaintFlowQuery/QueriesSet object.
     *
     * @param className Fully qualified class name
     * @param javaCode  Java code
     * @param fileName  File name of the Java source code.
     */
    private static void compileAndGetTaintFlowQueryObject(String className, String javaCode, String fileName) {
        try {
            // Loads the Java code.
            Class<?> specClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);

            Constructor<?> constructor = specClass.getConstructors()[0];

            Object ob = null;
            try {
                ob = constructor.newInstance(null);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if (ob instanceof FluentTQLUserInterface) {
                FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) ob;
                fluentTQLSpecs.put(fileName, fluentTQLUserInterface);
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns all the FluentTQL specifications that are present in the given path.
     *
     * @param path Path
     * @return HashMap that maps the fully qualified path name with the FluentTQL Specification object
     */
    public static HashMap<String, FluentTQLUserInterface> getSpecs(String path) {

        fluentTQLSpecs.clear();
        fileList.clear();

        try {
            getListOfFiles(path);
            for (String fileName : fileList) {
                File file = new File(fileName);

                readJavaCode(file.getName(), file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fluentTQLSpecs;
    }
}