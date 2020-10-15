package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import com.ibm.wala.classLoader.Module;

import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.ErrorModel;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.Errors;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.JarClassLoaderUtils;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal.SecuCheckAnalysisWrapper;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal.SecucheckMagpieBridgeAnalysis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import magpiebridge.core.AnalysisConsumer;
import magpiebridge.core.AnalysisResult;
import magpiebridge.core.ServerAnalysis;
import magpiebridge.core.ToolAnalysis;
import magpiebridge.core.analysis.configuration.ConfigurationAction;
import magpiebridge.core.analysis.configuration.ConfigurationOption;
import magpiebridge.core.analysis.configuration.OptionType;
import magpiebridge.core.analysis.configuration.htmlElement.CheckBox;
import magpiebridge.projectservice.java.JavaProjectService;

import org.apache.commons.io.FileUtils;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the FluentTQL Taint analysis. This implements the configuration options for configuration pages.
 * And uses these configuration options set by the user and set the FluentTQL analysis.
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLAnalysis implements ToolAnalysis, ServerAnalysis {

    private static final Logger logger = Logger.getLogger("main");

    private static final String FLUENT_SPEC_PATH_CONFIG_CONS = "FluentTQL Specifications path (Directory containing compiled Jar):  ";
    private static final String RECOMPILE_CONFIG_CONS = "Re-Compile fluentTQL specifications";
    private static final String FLUENT_FILES_CONFIG_CONS = "FluentTQL Specification files:";
    private static final String ENTRY_POINTS_CONFIG_CONS = "Select java files for entry points:";

    private static final List<String> entryPoints = new ArrayList<>();
    private static final Set<Path> classPath = new HashSet<>();
    public static final Set<Path> sourcePath = new HashSet<>();
    private static final Set<Path> libraryPath = new HashSet<>();
    private static final List<TaintFlowQuery> taintFlowQueries = new ArrayList<>();
    private static final List<ConfigurationOption> currentConfiguration = new ArrayList<>();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();
    private static String fluentTQLSpecPath = "";

    private static Path projectRootPath = null;

    private final Set<String> classNames = new HashSet<>();
    private final List<ConfigurationOption> options = new ArrayList<>();
    private final HashMap<String, String> listOfJavaFiles = new HashMap<>();
    private final SecucheckMagpieBridgeAnalysis secucheckAnalysis = new SecuCheckAnalysisWrapper(true);

    private boolean isFirstPageDone = false;
    private boolean goBackToPreviousPage = false;
    private String source = null;
    private JavaProjectService javaProjectService = null;
    private Future<?> lastAnalysisTask;
    private ExecutorService execService;

    /**
     * Constructor sets the initial configuration option for the configuration page.
     */
    public FluentTQLAnalysis() {
        initialConfigurationOption();
        currentConfiguration.addAll(options);
        execService = Executors.newSingleThreadExecutor();
    }

    /**
     * Initial configuration option of the FluentTQL Magpie server.
     */
    private void initialConfigurationOption() {
        options.clear();
        ConfigurationOption specPathRequesting = new ConfigurationOption(FLUENT_SPEC_PATH_CONFIG_CONS, OptionType.text);
        options.add(specPathRequesting);
    }

    /**
     * Returns the source name for the Magpie bridge required for configuration page.
     *
     * @return Source name
     */
    public String source() {

        if (javaProjectService == null) {
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

        return source;
    }

    /**
     * This method runs the analysis
     *
     * @param files  Files
     * @param server Analysis Server
     * @param rerun  Is rerun
     */
    public void analyze(Collection<? extends Module> files, AnalysisConsumer server, boolean rerun) {
        if (lastAnalysisTask != null && !lastAnalysisTask.isDone()) {
            lastAnalysisTask.cancel(false);
            if (lastAnalysisTask.isCancelled()) {
                logger.log(Level.INFO, "Last running analysis has been cancelled.");
            }
        }

        // Perform validation synchronously and run analysis asynchronously.
        if (validateQueriesAndEntryPoints()) {
            Runnable analysisTask = () -> {
                try {
                    Collection<AnalysisResult> results = secucheckAnalysis.run(taintFlowQueries,
                            entryPoints, classPath, libraryPath, projectRootPath.toAbsolutePath().toString());

                    server.consume(results, "secucheck-analysis");
                } catch (Exception e) {
                    FluentTQLMagpieBridgeMainServer.fluentTQLMagpieServer
                            .forwardMessageToClient(new MessageParams(MessageType.Error,
                                    "Problem occured while running the analysis: " + e.getMessage()));
                    logger.log(Level.SEVERE, "Problem occured while running the analysis: " + e.getMessage());
                }
            };

            lastAnalysisTask = execService.submit(analysisTask);
        }
    }

    private boolean validateQueriesAndEntryPoints() {
        if ((taintFlowQueries.size() == 0) || (entryPoints.size() == 0)) {
            if (isFirstPageDone) {
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
            } else {
                FluentTQLMagpieBridgeMainServer
                        .fluentTQLMagpieServer
                        .forwardMessageToClient(
                                new MessageParams(MessageType.Warning,
                                        "Please give the path of the fluentTQL specifications.")
                        );
            }
            return false;
        }
        return true;
    }

    /**
     * This method returns the configuration options to the Magpie bridge for configuration page.
     *
     * @return List of configuration options
     */
    public List<ConfigurationOption> getConfigurationOptions() {
        return options;
    }

    /**
     * This method returns the configuration actions to the Magpie bridge for configuration page.
     *
     * @return List of configuration actions
     */
    public List<ConfigurationAction> getConfiguredActions() {
        return Collections.emptyList();
    }

    /**
     * This method creates a FluentTQLError.txt with the errors found while processing the FluentTQL specifications
     *
     * @param errors Errors
     */
    private void createErrorText(Errors errors) {
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
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning,
                                    "There some errors while processing some specifications. Please check the below file for more details.\n" +
                                            file.getAbsolutePath().toString())
                    );
        }
    }

    /**
     * This method recursively extract the Jar files present in the given directory and copies into the outDirectory.
     * This method scans only till the depth level of 20 and scans only 20 sub-directories in the current directory.
     *
     * @param dir       Directory to scan for Jar files
     * @param depth     Current depth of the sub-directories
     * @param outPutDir Out put directory to copy the Jar files
     */
    private void extractJar(File dir, int depth, String outPutDir) {
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
    private void deleteDir(File dir) {
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method processes the FluentTQL Specification path configuration option from the first Configuration page.
     *
     * @param configOption Configuration option
     * @return ConfigurationOption - alert configuration if there is an error otherwise it returns null
     */
    private boolean processFluentTQLSpecificationsPath(ConfigurationOption configOption) {
        String specPath = configOption.getValue();
        fluentTQLSpecPath = specPath;

        if (specPath == null || "".equals(specPath)) {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning, "FluentTQL Specification's path is invalid!!!")
                    );
            isFirstPageDone = false;
            return false;
        }

        File file = new File(specPath);

        if (file.exists()) {
            if (file.isDirectory()) {

                File out = new File(file.getAbsolutePath() + System.getProperty("file.separator") + "fluentTQLTemp");

                if (out.exists())
                    deleteDir(out);

                out.mkdir();

                extractJar(file, 0, out.getAbsolutePath());

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

                if (files.length == 0) {
                    FluentTQLMagpieBridgeMainServer
                            .fluentTQLMagpieServer
                            .forwardMessageToClient(
                                    new MessageParams(
                                            MessageType.Warning,
                                            "Given Path does not have any Jar files.\n" +
                                                    "FluentTQL scans upto:\n" +
                                                    "Max sub-directories in current dir = 20\n" +
                                                    "Max depth of sub-directories = 20")
                            );
                    isFirstPageDone = false;
                    deleteDir(out);
                    return false;
                }

                fluentTQLSpecs.clear();
                JarClassLoaderUtils jarClassLoaderUtils = new JarClassLoaderUtils();

                //Todo:
                fluentTQLSpecs.putAll(jarClassLoaderUtils.loadAppAndGetFluentTQLSpecification(out.getAbsolutePath()));
                //fluentTQLSpecs.putAll(InternalFluentTQLIntegration.getSpecs(file.getAbsolutePath()));

                deleteDir(out);

                createErrorText(jarClassLoaderUtils.getErrorModel());

                if (fluentTQLSpecs.size() > 0) {
                    setConfig();
                    currentConfiguration.clear();
                    currentConfiguration.addAll(options);
                } else {
                    FluentTQLMagpieBridgeMainServer
                            .fluentTQLMagpieServer
                            .forwardMessageToClient(
                                    new MessageParams(MessageType.Warning, "No FluentTQL specifications present in the given path!!!")
                            );
                    isFirstPageDone = false;
                    return false;
                }
            } else {
                FluentTQLMagpieBridgeMainServer
                        .fluentTQLMagpieServer
                        .forwardMessageToClient(
                                new MessageParams(MessageType.Warning, "Given FluentTQL Specification's path is not a directory!!! \nPlease give valid directory name.")
                        );
                isFirstPageDone = false;
                return false;
            }
        } else {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning, "Given FluentTQL Specification's path does not exist!!!")
                    );
            isFirstPageDone = false;
            return false;
        }

        setConfigWithJavaFiles(classNames);
        isFirstPageDone = true;

        ConfigurationOption recompileOption = new ConfigurationOption(RECOMPILE_CONFIG_CONS, OptionType.checkbox);
        List<ConfigurationOption> tempOptions = new ArrayList<>();
        tempOptions.add(recompileOption);
        tempOptions.addAll(options);

        options.clear();
        options.addAll(tempOptions);
        currentConfiguration.addAll(options);
        return true;
    }

    /**
     * This method processes the FluentTQL Specification files configuration option from the second Configuration page.
     *
     * @param configOption Configuration option
     * @return Boolean - process success or not
     * 0 - Success.
     * 50 - No specifications are selected, go ahead and process Java Entry files configuration.
     * 100 - GoBackToPreviousPage.
     * 150 - ConfigurationPage Updated return immediately, do not process remaining current configuration settings.
     */
    private int processFluentTQLSpecificationFiles(ConfigurationOption configOption, boolean isRecompile) {
        taintFlowQueries.clear();

        if (isRecompile) {
            fluentTQLSpecs.clear();

            ConfigurationOption temp = new ConfigurationOption(fluentTQLSpecPath, OptionType.checkbox);
            temp.setValue(fluentTQLSpecPath);
            boolean isSuccess = processFluentTQLSpecificationsPath(temp);

            if (!isSuccess) {
                goBackToPreviousPage = true;
                return 100;
            } else {
                return 150;
            }
            /*
            JarClassLoaderUtils jarClassLoaderUtils = new JarClassLoaderUtils();

            //Todo:
            fluentTQLSpecs.putAll(jarClassLoaderUtils.loadAppAndGetFluentTQLSpecification(fluentTQLSpecPath));
            //fluentTQLSpecs.putAll(InternalFluentTQLIntegration.getSpecs(fluentTQLSpecPath));

            //Todo: use this create a error file in source path.*/
        }

        goBackToPreviousPage = false;

        int selectedCount = 0;

        for (ConfigurationOption configurationOption : configOption.getChildren()) {
            if (configurationOption.getValueAsBoolean()) {
                selectedCount += 1;

                // Get the list of FluentTQLSpecification
                List<FluentTQLSpecification> fluentTQLSpecificationList = fluentTQLSpecs.get(configurationOption.getName()).getFluentTQLSpecification();

                addTaintFLowQueries(fluentTQLSpecificationList);
            }
        }

        options.clear();
        options.addAll(currentConfiguration);
        if (selectedCount == 0) {

            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning,
                                    "No specifications are selected. \nPlease select the FluentTQL specifications"
                            ));

            return 50;
        }
        return 0;
    }

    /**
     * This method processes the Java files configuration option from the second Configuration page.
     *
     * @param configOption Configuration option
     * @return Boolean - process success or not
     */
    private boolean processJavaFiles(ConfigurationOption configOption) {
        entryPoints.clear();

        int selectedCount = 0;

        for (ConfigurationOption configurationOption : configOption.getChildren()) {
            if (configurationOption.getValueAsBoolean()) {
                selectedCount += 1;

                entryPoints.add(listOfJavaFiles.get(configurationOption.getName().replace(".java", "")));
            }
        }

        options.clear();
        options.addAll(currentConfiguration);
        if (selectedCount == 0) {

            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning,
                                    "No Java files are selected as entry points. \nPlease select the Java files for entry points"
                            ));
            return false;
        }
        return true;
    }

    /**
     * This method configures the analysis or configuration page based on the user input given in the previous
     * configuration page.
     *
     * @param configuration List of configuration options from the Magpie bridge
     */
    public void configure(List<ConfigurationOption> configuration) {
        currentConfiguration.clear();
        currentConfiguration.addAll(configuration);

        boolean specFileSuccess = false;
        boolean entryFileSuccess = false;
        boolean isRecompile = false;

        for (ConfigurationOption configOption : configuration) {
            if (RECOMPILE_CONFIG_CONS.equals(configOption.getName())) {
                isRecompile = configOption.getValueAsBoolean();
            }
        }

        for (ConfigurationOption configOption : configuration) {

            if (FLUENT_SPEC_PATH_CONFIG_CONS.equals(configOption.getName())) {
                boolean isSuccess = processFluentTQLSpecificationsPath(configOption);

                if (!isSuccess) {
                    configOption.setValue("");
                    options.clear();
                    options.addAll(configuration);

                    return;
                }
            } else if (FLUENT_FILES_CONFIG_CONS.equals(configOption.getName())) {
                int status = processFluentTQLSpecificationFiles(configOption, isRecompile);

                if (status == 100) {
                    initialConfigurationOption();
                    return;
                } else if (status == 150) {
                    return;
                } else if (status == 0) {
                    specFileSuccess = true;
                } else {
                    specFileSuccess = false;
                }
            } else if (ENTRY_POINTS_CONFIG_CONS.equals(configOption.getName())) {
                boolean isSuccess = processJavaFiles(configOption);

                entryFileSuccess = isSuccess;
                if (!isSuccess) {
                    options.clear();
                    options.addAll(configuration);
                }
            }
        }

        if (specFileSuccess && entryFileSuccess) {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Info,
                                    "Configuration submitted successfully."
                            ));
        }
    }

    /**
     * This method adds all the TaintFlowQuery objects from the FluentTQLSpecification into a global variable
     *
     * @param fluentTQLSpecificationList List of all the FluentTQLSpecification objetcs.
     */
    private void addTaintFLowQueries(List<FluentTQLSpecification> fluentTQLSpecificationList) {
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
     * This method sets the new configuration option for new configuration page.
     */
    private void setConfig() {
        taintFlowQueries.clear();

        CheckBox initialOption = new CheckBox(
                FLUENT_FILES_CONFIG_CONS,
                "true");

        Set<String> keys = FluentTQLAnalysis.fluentTQLSpecs.keySet();

        for (String key : keys) {
            CheckBox myOption = new CheckBox(
                    key,
                    "true"
            );

            initialOption.addChild(myOption);

            addTaintFLowQueries(
                    FluentTQLAnalysis.fluentTQLSpecs.get(key).getFluentTQLSpecification()
            );
        }

        options.clear();
        options.add(initialOption);
    }

    /**
     * This method sets the configuration options for Java files as entry point for the second configuration page from the
     * first configuration page input.
     *
     * @param classNames Set of fully qualified class names.
     */
    private void setConfigWithJavaFiles(Set<String> classNames) {
        CheckBox initialOption = new CheckBox(
                ENTRY_POINTS_CONFIG_CONS,
                "true");

        List<String> sortedClassNames = new ArrayList<>();

        for (String javaFile : classNames) {
            String[] str = javaFile.split("\\.");
            sortedClassNames.add(str[str.length - 1]);

            listOfJavaFiles.put(str[str.length - 1], javaFile);
        }

        Collections.sort(sortedClassNames);

        for (String javaFile : sortedClassNames) {
            entryPoints.add(listOfJavaFiles.get(javaFile));
            //      javaFilesAsEntryPoints.add(javaFile);
            String[] str = javaFile.split("\\.");
            String key = str[str.length - 1];
            CheckBox myOption = new CheckBox(
                    key + ".java",
                    "true"
            );

            initialOption.addChild(myOption);
        }

        options.add(initialOption);
    }

    public String[] getCommand() {
        return new String[0];
    }

    public Collection<AnalysisResult> convertToolOutput() {
        return null;
    }
}
