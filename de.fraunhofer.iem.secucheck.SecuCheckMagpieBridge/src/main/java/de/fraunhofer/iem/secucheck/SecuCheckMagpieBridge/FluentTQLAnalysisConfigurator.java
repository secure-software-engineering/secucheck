package de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge;

import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.ErrorModel;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.Errors;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.JarClassLoaderUtils;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.DuplicateTaintFlowQueryIDException;
import de.fraunhofer.iem.secucheck.JarUtility;
import de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.SecucheckHttpServer.utility.PrintUtility;
import de.fraunhofer.iem.secucheck.SecuCheckMagpieBridge.internal.SecuCheckAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import de.fraunhofer.iem.secucheck.analysis.query.Solver;
import j2html.tags.ContainerTag;
import j2html.tags.EmptyTag;
import magpiebridge.projectservice.java.JavaProjectService;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

import static j2html.TagCreator.*;

public class FluentTQLAnalysisConfigurator {
    /**
     * List of TaintFlowQueries configured by the user, this is passed to Analysis
     */
    private static final List<TaintFlowQuery> taintFlowQueries = new ArrayList<>();

    /**
     * Set of strings that represents the entry points class for Analysis, this is configured by the user and passed to Analysis
     */
    private static final HashSet<String> entryPoints = new HashSet<>();

    /**
     * Set of Path represents the class path of the project. This is passed to Analysis.
     */
    private static final Set<Path> classPath = new HashSet<>();

    /**
     * Set of path represents the library path of the project. This is passed to the Analysis.
     */
    private static final Set<Path> libraryPath = new HashSet<>();

    /**
     * This is the Project root path. This is passed to the Analysis.
     */
    private static Path projectRootPath = null;

    /**
     * Specification's path
     */
    private static String fluentTQLSpecPath = "";

    /**
     * HashMap, contains the FluentTQL specification file name and its corresponding FluentTQLUserInterface object.
     */
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    /**
     * Set of strings that contains the entry points as method
     */
    private static final HashSet<String> entryPointsAsMethod = new HashSet<>();

    /**
     * Set of FluentTQL Method that represents the General Propagators
     */
    private static final HashSet<Method> generalPropagators = new HashSet<>();


    private static final Set<String> classNames = new HashSet<>();

    /**
     * This represents the currently displaying HTML configuration page.
     */
    private static String currentConfigHtmlPage = "";

    /**
     * Analysis solver. By default its Boomerang3
     */
    private static Solver analysisSolver = Solver.BOOMERANG3;

    private static final Set<Path> sourcePath = new HashSet<>();


    private static final HashMap<String, String> listOfJavaFiles = new HashMap<>();


    private static String source = null;
    private static JavaProjectService javaProjectService = null;

    public static String getSource() {
        return source;
    }

    public static void setSource(String source) {
        FluentTQLAnalysisConfigurator.source = source;
    }

    public static String getCurrentConfigHtmlPage() {
        return currentConfigHtmlPage;
    }

    public static void setCurrentConfigHtmlPage(String currentConfigHtmlPage) {
        FluentTQLAnalysisConfigurator.currentConfigHtmlPage = currentConfigHtmlPage;
    }

    public static Set<Path> getSourcePath() {
        return sourcePath;
    }

    /**
     * Setter
     *
     * @param solver Analysis solver
     */
    public static void setAnalysisSolver(Solver solver) {
        FluentTQLAnalysisConfigurator.analysisSolver = solver;
    }

    /**
     * This method processes the FluentTQL Specification path configuration option from the first Configuration page.
     *
     * @param specPath Specification's path
     * @return ConfigurationOption - alert configuration if there is an error otherwise it returns null
     */
    public static boolean processFluentTQLSpecificationsPath(String specPath) {
        fluentTQLSpecPath = specPath;

        if (specPath == null || "".equals(specPath)) {
            PrintUtility.printMessageInIDE(MessageType.Warning, "FluentTQL Specification's path is invalid!!!");
            //isFirstPageDone = false;
            return false;
        }

        File file = new File(specPath);

        if (file.exists()) {
            if (file.isDirectory()) {

                File out = new File(file.getAbsolutePath() + System.getProperty("file.separator") + "fluentTQLTemp");

                File[] files = JarUtility.extractJar(file, out);

                if (files.length == 0) {
                    PrintUtility.printMessageInIDE(MessageType.Warning,
                            "Given Path does not have any Jar files.\n" +
                                    "FluentTQL scans upto:\n" +
                                    "Max sub-directories in current dir = 20\n" +
                                    "Max depth of sub-directories = 20");
                    //isFirstPageDone = false;
                    JarUtility.deleteDir(out);
                    return false;
                }

                fluentTQLSpecs.clear();
                entryPointsAsMethod.clear();
                generalPropagators.clear();

                JarClassLoaderUtils jarClassLoaderUtils = new JarClassLoaderUtils();

                //Todo:
                try {
                    fluentTQLSpecs.putAll(jarClassLoaderUtils.loadAppAndGetFluentTQLSpecification(out.getAbsolutePath()));
                } catch (DuplicateTaintFlowQueryIDException e) {
                    PrintUtility.printMessageInIDE(MessageType.Warning,
                            e.getMessage());
                    return false;
                }
                //fluentTQLSpecs.putAll(InternalFluentTQLIntegration.getSpecs(file.getAbsolutePath()));

                JarUtility.deleteDir(out);

                createErrorText(jarClassLoaderUtils.getErrorModel());
                entryPointsAsMethod.addAll(jarClassLoaderUtils.getEntryPoints());
                generalPropagators.addAll(jarClassLoaderUtils.getGeneralPropagators());

                if (fluentTQLSpecs.size() > 0) {
                    return true;
                } else {
                    PrintUtility.printMessageInIDE(MessageType.Warning,
                            "No FluentTQL specifications present in the given path!!!");
                    //isFirstPageDone = false;
                    return false;
                }
            } else {
                PrintUtility.printMessageInIDE(MessageType.Warning,
                        "Given FluentTQL Specification's path is not a directory!!! \nPlease give valid directory name.");
                //isFirstPageDone = false;
                return false;
            }
        } else {
            PrintUtility.printMessageInIDE(MessageType.Warning,
                    "Given FluentTQL Specification's path does not exist!!!");
            //isFirstPageDone = false;
            return false;
        }
    }

    public static boolean isNullJavaProjectService() {
        return javaProjectService == null;
    }

    public static void setProjectInformation() {
        javaProjectService = (JavaProjectService) FluentTQLMagpieBridgeMainServer.fluentTQLMagpieServer.getProjectService("java").get();

        projectRootPath = javaProjectService.getRootPath().get();

        libraryPath.clear();
        libraryPath.addAll(javaProjectService.getLibraryPath());

        classPath.clear();
        classPath.addAll(javaProjectService.getClassPath());
        classPath.removeAll(javaProjectService.getLibraryPath());

        sourcePath.addAll(javaProjectService.getSourcePath());

        classNames.clear();
        classNames.addAll(javaProjectService.getSourceClassFullQualifiedNames());

        String[] str = javaProjectService.getRootPath().get().toString().split("\\\\");
        source = str[str.length - 1];
    }

    /**
     * This method sets the new configuration option for new configuration page.
     */
    public static String setConfig() {
        taintFlowQueries.clear();

        Set<String> keys = fluentTQLSpecs.keySet();
        ContainerTag[] tags = new ContainerTag[keys.size()];

        int i = 0;
        for (String key : keys) {
            EmptyTag temp = input()
                    .withClass("checkboxes-input fluentSpecClass")
                    .withType("checkbox")
                    .withName(key + "-specs")
                    .withId(key + "-specs");
            temp.attr("checked");

            ContainerTag aTag = a(
                    label(i(" " + key)).attr("for", key + "-specs")
            ).withHref("#");

            ContainerTag temp1 = li(div(join(temp, aTag)));

            tags[i++] = temp1;


            addTaintFLowQueries(
                    fluentTQLSpecs.get(key).getFluentTQLSpecification()
            );
        }

        return ul(tags)
                .withClass("nav")
                .withId("fluentSpecsLists")
                .withStyle("font-size:18px")
                .renderFormatted();
    }

    /**
     * This method sets the configuration options for Java files as entry point for the second configuration page from the
     * first configuration page input.
     */
    public static String setConfigWithJavaFiles() {
        List<String> sortedClassNames = new ArrayList<>(classNames);
/*
        for (String javaFile : classNames) {
            String[] str = javaFile.split("\\.");
            sortedClassNames.add(str[str.length - 1]);

            listOfJavaFiles.put(str[str.length - 1], javaFile);
        }
*/
        Collections.sort(sortedClassNames);

        ContainerTag[] tags = new ContainerTag[sortedClassNames.size()];

        int i = 0;
        for (String javaFile : sortedClassNames) {
            entryPoints.add(javaFile);
            //      javaFilesAsEntryPoints.add(javaFile);

            //String[] str = javaFile.split("\\.");
            //String key = str[str.length - 1];

            EmptyTag temp = input()
                    .withClass("checkboxes-input entryPointsClass")
                    .withType("checkbox")
                    .withName(javaFile + ".java" + "-entryPoint")
                    .withId(javaFile + ".java" + "-entryPoint");
            temp.attr("checked");

            ContainerTag aTag = a(
                    label(i(" " + javaFile)).attr("for", javaFile + ".java" + "-entryPoint")
            ).withHref("#");

            ContainerTag temp1 = li(div(join(temp, aTag)));

            tags[i++] = temp1;
        }

        return ul(tags)
                .withClass("nav")
                .withId("entryPointsLists")
                .renderFormatted();
    }

    /**
     * This method adds all the TaintFlowQuery objects from the FluentTQLSpecification into a global variable
     *
     * @param fluentTQLSpecificationList List of all the FluentTQLSpecification objetcs.
     */
    public static void addTaintFLowQueries(List<FluentTQLSpecification> fluentTQLSpecificationList) {
        // Get the TaintFLowQuery object.
        for (FluentTQLSpecification fluentTQLSpecification : fluentTQLSpecificationList) {
            if (fluentTQLSpecification instanceof TaintFlowQuery) {
                TaintFlowQuery taintFlowQuery = (TaintFlowQuery) fluentTQLSpecification;

                taintFlowQueries.add(taintFlowQuery);
            } else if (fluentTQLSpecification instanceof QueriesSet) {
                QueriesSet queriesSet = (QueriesSet) fluentTQLSpecification;

                taintFlowQueries.addAll(queriesSet.getTaintFlowQueries());
            }
        }
    }

    /**
     * This method creates a FluentTQLError.txt with the errors found while processing the FluentTQL specifications
     *
     * @param errors Errors
     */
    private static void createErrorText(Errors errors) {
        if (errors.getErrors().size() > 0) {
            File file = new File(projectRootPath + System.getProperty("file.separator") + "FluentTQLError.txt");

            try {
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                }

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

                bufferedWriter.write("FluentTQL:     " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");

                for (ErrorModel errorModel : errors.getErrors()) {
                    bufferedWriter.append(errorModel.toString());
                }

                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintUtility.printMessageInIDE(MessageType.Warning,
                    "There some errors while processing some specifications. Please check the below file for more details.\n" +
                            file.getAbsolutePath().toString());
        }
    }

    /**
     * This method processes the FluentTQL Specification files configuration option from the second Configuration page.
     *
     * @return Boolean - process success or not
     * 0 - Success.
     * 50 - No specifications are selected, go ahead and process Java Entry files configuration.
     * 100 - GoBackToPreviousPage.
     * 150 - ConfigurationPage Updated return immediately, do not process remaining current configuration settings.
     */
    public static boolean processFluentTQLSpecificationFiles(Set<String> specList) {
        taintFlowQueries.clear();

/*        if (isRecompile) {
            fluentTQLSpecs.clear();

            ConfigurationOption temp = new ConfigurationOption(fluentTQLSpecPath, OptionType.checkbox);
            temp.setValue(fluentTQLSpecPath);
    /*        boolean isSuccess = processFluentTQLSpecificationsPath(temp);

            if (!isSuccess) {
                goBackToPreviousPage = true;
                return 100;
            } else {
                return 150;
            }
        }*/

        if (specList.size() == 0) {
            PrintUtility.printMessageInIDE(MessageType.Warning,
                    "No specifications are selected. \nPlease select the FluentTQL specifications");
            return false;
        }

        for (String spec : specList) {
            List<FluentTQLSpecification> fluentTQLSpecificationList = fluentTQLSpecs.get(
                    spec.split("-")[0]).getFluentTQLSpecification();

            addTaintFLowQueries(fluentTQLSpecificationList);
        }

        return true;
    }

    /**
     * This method processes the Java files configuration option from the second Configuration page.
     *
     * @return Boolean - process success or not
     */
    public static boolean processJavaFiles(Set<String> entryPointList) {
        entryPoints.clear();

        if (entryPointList.size() == 0) {
            PrintUtility.printMessageInIDE(MessageType.Warning,
                    "No Java files are selected as entry points. \nPlease select the Java files for entry points");
            return false;
        }

        for (String entryPoint : entryPointList) {
            entryPoints.add(entryPoint.split("-")[0].replace(".java", ""));
        }

        return true;
    }

    public static boolean validateQueriesAndEntryPoints() {
        if ((taintFlowQueries.size() == 0) || (entryPoints.size() == 0)) {

            String message;
            if ((taintFlowQueries.size() == 0) && (entryPoints.size() == 0))
                message = "Please select both FluentTQL specification files and Java files for entry points.";
            else if (taintFlowQueries.size() == 0)
                message = "Please select FluentTQL specification files.";
            else
                message = "Please select Java files for entry points.";

            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning,
                                    message)
                    );

            return false;
        }
        return true;
    }

    public static void printForNow() {
        System.out.println("***************************************************\n\n");
        System.out.println("TaintFlowQueries = " + taintFlowQueries);
        System.out.println("EntryPoint = " + entryPoints);
        System.out.println("Class Path = " + classPath);
        System.out.println("Library Path = " + libraryPath);
        System.out.println("Project Root path = " + projectRootPath.toAbsolutePath().toString());
        System.out.println("\n\n***************************************************");
    }

    public static void runAnalysis() {
        SecuCheckAnalysisConfigurator.run(taintFlowQueries, analysisSolver);
    }

    public static String getClassPathAsString() {
        String classPathAsString = "";

        for (Path path : classPath) {
            if (!path.toString().contains("bin") && path.toFile().exists())
                classPathAsString += path.toString() + File.pathSeparator;
        }

        return classPathAsString;
    }

    public static HashSet<String> getEntryPoints() {
        return entryPoints;
    }
}
