package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import com.ibm.wala.classLoader.Module;
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
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

import java.io.File;
import java.util.*;

/**
 * This class is the FluentTQL Taint analysis. This implements the configuration options for configuration pages.
 * And uses these configuration options set by the user and set the FluentTQL analysis.
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLAnalysis implements ToolAnalysis, ServerAnalysis {

    private final List<ConfigurationOption> options = new ArrayList<>();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();
    private final HashMap<String, String> listOfJavaFiles = new HashMap<>();
    private static final List<ConfigurationOption> currentConfiguration = new ArrayList<>();
    private boolean firstConfigPageSuccess = false;

    //Final variables to be sent to the analysis.
    private static final List<TaintFlowQuery> listOfConfiguredTaintFlowQueries = new ArrayList<>();
    private static final List<String> javaFilesAsEntryPoints = new ArrayList<>();

    /**
     * Constructor sets the initial configuration option for the configuration page.
     */
    public FluentTQLAnalysis() {
        initialConfigurationOption();
        currentConfiguration.addAll(options);
    }

    /**
     * Initial configuration option of the FluentTQL Magpie server.
     */
    private void initialConfigurationOption() {
        options.clear();
        ConfigurationOption specPathRequesting = new ConfigurationOption("FluentTQL Specification's path", OptionType.text);
        ConfigurationOption javaPathRequesting = new ConfigurationOption("Path of Java files for entry points", OptionType.text);
        options.add(specPathRequesting);
        options.add(javaPathRequesting);
    }

    /**
     * Returns the source name for the Magpie bridge required for configuration page.
     *
     * @return Source name
     */
    public String source() {
        return "FluentTQLAnalysis";
    }

    /**
     * This method runs the analysis
     *
     * @param files  Files
     * @param server Analysis Server
     * @param rerun  Is rerun
     */
    public void analyze(Collection<? extends Module> files, AnalysisConsumer server, boolean rerun) {
        //Todo: Add running analysis code here.

        if ((listOfConfiguredTaintFlowQueries.size() == 0) || (javaFilesAsEntryPoints.size() == 0)) {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning,
                                    "Please give the path of the fluentTQL specifications and the Java files.")
                    );
        } else {
            System.out.println("Analysis is on progress");

            System.out.println("FluentTQL Specification: ");

            for (FluentTQLSpecification fluentTQLSpecification : listOfConfiguredTaintFlowQueries) {
                System.out.println("\t" + fluentTQLSpecification.toString());
            }

            System.out.println("Java files as entry points: ");

            for (String javaFiles : javaFilesAsEntryPoints) {
                System.out.println("\t" + javaFiles);
            }
        }
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
     * This method processes the FluentTQL Specification path configuration option from the first Configuration page.
     *
     * @param configOption Configuration option
     * @return ConfigurationOption - alert configuration if there is an error otherwise it returns null
     */
    private boolean processFluentTQLSpecificationsPath(ConfigurationOption configOption) {
        String specPath = configOption.getValue();

        if (specPath == null || "".equals(specPath)) {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning, "FluentTQL Specification's path is invalid!!!")
                    );
            firstConfigPageSuccess = false;
            return false;
        }

        File file = new File(specPath);

        if (file.exists()) {
            if (file.isDirectory()) {
                fluentTQLSpecs.clear();
                fluentTQLSpecs.putAll(InternalFluentTQLIntegration.getSpecs(file.getAbsolutePath()));

                if (fluentTQLSpecs.size() > 0) {
                    setConfig();
                    firstConfigPageSuccess = true;
                    currentConfiguration.clear();
                    currentConfiguration.addAll(options);
                    return true;
                } else {
                    FluentTQLMagpieBridgeMainServer
                            .fluentTQLMagpieServer
                            .forwardMessageToClient(
                                    new MessageParams(MessageType.Warning, "No FluentTQL specifications present in the given path!!!")
                            );
                    firstConfigPageSuccess = false;
                    return false;
                }
            } else {
                FluentTQLMagpieBridgeMainServer
                        .fluentTQLMagpieServer
                        .forwardMessageToClient(
                                new MessageParams(MessageType.Warning, "Given FluentTQL Specification's path is not a directory!!! \nPlease give valid directory name.")
                        );
                firstConfigPageSuccess = false;
                return false;
            }
        } else {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning, "Given FluentTQL Specification's path does not exist!!!")
                    );
            firstConfigPageSuccess = false;
            return false;
        }
    }

    /**
     * This method processes the FluentTQL Specification files configuration option from the second Configuration page.
     *
     * @param configOption Configuration option
     * @return Boolean - process success or not
     */
    private boolean processFluentTQLSpecificationFiles(ConfigurationOption configOption) {
        listOfConfiguredTaintFlowQueries.clear();

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

            return false;
        }
        return true;
    }

    /**
     * This method processes the Java files configuration option from the second Configuration page.
     *
     * @param configOption Configuration option
     * @return Boolean - process success or not
     */
    private boolean processJavaFiles(ConfigurationOption configOption) {
        javaFilesAsEntryPoints.clear();

        int selectedCount = 0;

        for (ConfigurationOption configurationOption : configOption.getChildren()) {
            if (configurationOption.getValueAsBoolean()) {
                selectedCount += 1;

                javaFilesAsEntryPoints.add(listOfJavaFiles.get(configurationOption.getName()));
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
     * This method returns the list of all the Java file in the given path.
     *
     * @param javaFile Path to be searched for Java files
     */
    private void getListOfFiles(File javaFile) {
        File[] files = javaFile.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String filePath = file.getAbsolutePath();

                if (file.isDirectory()) {
                    getListOfFiles(new File(filePath));
                } else if (fileName.endsWith(".java")) {
                    listOfJavaFiles.put(fileName, filePath);
                }
            }
        }
    }

    /**
     * This method processes the Java files as entry points configuration option from the first Configuration page.
     *
     * @param configOption Configuration option
     * @return ConfigurationOption - alert configuration if there is an error otherwise it returns null
     */
    private boolean processJavaFilesPath(ConfigurationOption configOption) {
        String javaPath = configOption.getValue();

        if (javaPath == null || "".equals(javaPath)) {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning, "Path of Java files for entry points is invalid!!!")
                    );
            return false;
        }

        File javaFile = new File(javaPath);


        if (javaFile.exists()) {
            if (javaFile.isDirectory()) {
                listOfJavaFiles.clear();
                getListOfFiles(javaFile);

                if (listOfJavaFiles.size() > 0) {
                    if (firstConfigPageSuccess) {
                        setConfigWithJavaFiles(listOfJavaFiles);
                        currentConfiguration.addAll(options);
                    }
                    return true;
                } else {
                    FluentTQLMagpieBridgeMainServer
                            .fluentTQLMagpieServer
                            .forwardMessageToClient(
                                    new MessageParams(MessageType.Warning, "No Java files present in the given path!!!")
                            );
                    return false;
                }
            } else {
                FluentTQLMagpieBridgeMainServer
                        .fluentTQLMagpieServer
                        .forwardMessageToClient(
                                new MessageParams(MessageType.Warning, "Given Path of Java files for entry points is not a directory!!! \nPlease give valid directory name.")
                        );
                return false;
            }
        } else {
            FluentTQLMagpieBridgeMainServer
                    .fluentTQLMagpieServer
                    .forwardMessageToClient(
                            new MessageParams(MessageType.Warning, "Given Path of Java files for entry points does not exist")
                    );
            return false;
        }
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

        for (ConfigurationOption configOption : configuration) {

            if ("FluentTQL Specification's path".equals(configOption.getName())) {
                boolean isSuccess = processFluentTQLSpecificationsPath(configOption);

                if (!isSuccess) {
                    configOption.setValue("");
                    options.clear();
                    options.addAll(configuration);
                }
            } else if ("Path of Java files for entry points".equals(configOption.getName())) {
                boolean isSuccess = processJavaFilesPath(configOption);

                if (!isSuccess) {
                    configOption.setValue("");
                    options.clear();
                    options.addAll(configuration);
                }
            } else if ("FluentTQL Specification files".equals(configOption.getName())) {
                boolean isSuccess = processFluentTQLSpecificationFiles(configOption);

                if (!isSuccess) {
                    options.clear();
                    options.addAll(configuration);
                }
            } else if ("Select java files for entry points".equals(configOption.getName())) {
                boolean isSuccess = processJavaFiles(configOption);

                if (!isSuccess) {
                    options.clear();
                    options.addAll(configuration);
                }
            }
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

                listOfConfiguredTaintFlowQueries.add(taintFlowQuery);
            } else if (fluentTQLSpecification instanceof QueriesSet) {
                QueriesSet queriesSet = (QueriesSet) fluentTQLSpecification;

                listOfConfiguredTaintFlowQueries.addAll(queriesSet.getTaintFlowQueries());
            }
        }
    }

    /**
     * This method sets the new configuration option for new configuration page.
     */
    private void setConfig() {
        listOfConfiguredTaintFlowQueries.clear();

        CheckBox initialOption = new CheckBox(
                "FluentTQL Specification files",
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
     * @param javaFiles HashMap - Java files.
     */
    private void setConfigWithJavaFiles(HashMap<String, String> javaFiles) {
        CheckBox initialOption = new CheckBox(
                "Select java files for entry points",
                "true");

        for (String javaFile : javaFiles.keySet()) {
            CheckBox myOption = new CheckBox(
                    javaFile,
                    "true"
            );

            initialOption.addChild(myOption);

            javaFilesAsEntryPoints.add(javaFile);
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
