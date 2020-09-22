package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import net.openhft.compiler.CompilerUtils;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

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
    private static final List<String> fileList = new ArrayList<>();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    /**
     * This method scans the given path recursively and retrieves all the Java files.
     *
     * @param pathName Path
     */
    private static void getListOfFiles(String pathName) {

        File f = new File(pathName);

        File[] files = f.listFiles();

        if (files == null)
            return;

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
    private static String[] readJavaCode(String fileName, String filePath) throws IOException {
        String[] result = new String[2];

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

        result[0] = className;
        result[1] = javaCode;

        return result;
    }

    /**
     * This method loads the Java code in to the currently running JVM and instantiates the object to get the
     * TaintFlowQuery/QueriesSet object.
     *
     * @param className Fully qualified class name
     * @param javaCode  Java code
     * @param fileName  File name of the Java source code.
     */
    private static boolean compileAndGetTaintFlowQueryObject(String className, String javaCode, String fileName) {
        try {
            ClassLoader classLoader = new ClassLoader() { };
            // Loads the Java code.
            Class<?> specClass = CompilerUtils.CACHED_COMPILER.loadFromJava(classLoader, className, javaCode);

            Constructor<?> constructor = specClass.getConstructors()[0];

            Object ob = null;
            try {
                ob = constructor.newInstance((Object[]) null);
            } catch (InvocationTargetException e) {
                return false;
            }

            if (ob instanceof FluentTQLUserInterface) {
                FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) ob;
                fluentTQLSpecs.put(fileName, fluentTQLUserInterface);
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return false;
        }
        return true;
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

        HashMap<String, String[]> fileWithClassAndJavaCode = new HashMap<>();

        int previousFailedCount = 999;

        try {
            //Try to get the source code.
            getListOfFiles(path);
            for (String fileName : fileList) {
                File file = new File(fileName);

                String[] javaCodeWithClassName = readJavaCode(file.getName(), file.getAbsolutePath());
                fileWithClassAndJavaCode.put(file.getName(), javaCodeWithClassName);
            }

            List<String> tempNotLoadedFileList = new ArrayList<>();
            List<String> notLoadedFileList = new ArrayList<>(fileList);

            do {
                previousFailedCount = notLoadedFileList.size();
                tempNotLoadedFileList.clear();

                for (String fileName : notLoadedFileList) {
                    File file = new File(fileName);

                    boolean isSuccess = compileAndGetTaintFlowQueryObject(
                            fileWithClassAndJavaCode.get(file.getName())[0],
                            fileWithClassAndJavaCode.get(file.getName())[1],
                            file.getName());

                    if (!isSuccess) {
                        tempNotLoadedFileList.add(fileName);
                    }
                }

                notLoadedFileList.clear();
                notLoadedFileList.addAll(tempNotLoadedFileList);
            } while (notLoadedFileList.size() > 0 && notLoadedFileList.size() < previousFailedCount);

            if (notLoadedFileList.size() != 0) {
                String printList = "";
                for (String fqFileName : notLoadedFileList) {
                    String[] fileName = fqFileName.split("\\\\");
                    printList += fileName[fileName.length - 1] + "\n";
                }

                FluentTQLMagpieBridgeMainServer
                        .fluentTQLMagpieServer
                        .forwardMessageToClient(
                                new MessageParams(MessageType.Warning,
                                        "Below FluentTQL specifications are invalid.\n" + printList)
                        );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fluentTQLSpecs;
    }
}