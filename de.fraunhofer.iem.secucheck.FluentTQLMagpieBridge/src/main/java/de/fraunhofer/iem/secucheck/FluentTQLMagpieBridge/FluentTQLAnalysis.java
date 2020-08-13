package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import com.ibm.wala.classLoader.Module;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import magpiebridge.core.AnalysisConsumer;
import magpiebridge.core.AnalysisResult;
import magpiebridge.core.ServerAnalysis;
import magpiebridge.core.ToolAnalysis;
import magpiebridge.core.analysis.configuration.ConfigurationAction;
import magpiebridge.core.analysis.configuration.ConfigurationOption;
import magpiebridge.core.analysis.configuration.OptionType;
import magpiebridge.core.analysis.configuration.htmlElement.CheckBox;
import magpiebridge.projectservice.java.JavaProjectService;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

import java.io.File;
import java.nio.file.Path;
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
    private boolean isFirstPageDone = false;
    private String source = null;
    private final Set<String> classNames = new HashSet<>();
    private JavaProjectService javaProjectService = null;

    //Final variables to be sent to the analysis.
    private static final List<TaintFlowQuery> listOfConfiguredTaintFlowQueries = new ArrayList<>();
    private static final List<String> javaFilesAsEntryPoints = new ArrayList<>();
    private static final Set<Path> classPath = new HashSet<>();
    private static final Set<Path> libraryPath = new HashSet<>();
    private static Path projectRootPath = null;


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
        if ((listOfConfiguredTaintFlowQueries.size() == 0) || (javaFilesAsEntryPoints.size() == 0)) {
            if (isFirstPageDone) {
                String message = "";
                if ((listOfConfiguredTaintFlowQueries.size() == 0) && (javaFilesAsEntryPoints.size() == 0))
                    message = "Please select both FluentTQL specification files and Java files for entry points.";
                else if (listOfConfiguredTaintFlowQueries.size() == 0)
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
        } else {
            //Todo: Call the API with the required arguments for the analysis. Below is the example on how to use TaintFlowQuery objects

            System.out.println("Analysis is on progress");

            System.out.println("####################################################################################");
            System.out.println("Java files as entry points: ");

            for (String javaFiles : javaFilesAsEntryPoints) {
                System.out.println("\t" + javaFiles);
            }

            System.out.println("####################################################################################");
            System.out.println("\nProject root path = " + projectRootPath + "\n");
            System.out.println("####################################################################################");
            System.out.println("\nProject class path = " + classPath + "\n");
            System.out.println("####################################################################################");
            System.out.println("\nProject library path = " + libraryPath + "\n");
            System.out.println("####################################################################################");

            System.out.println("FluentTQL Specification: ");

            for (TaintFlowQuery taintFlowQuery : listOfConfiguredTaintFlowQueries) {
                System.out.println("**************************************************************************");
                //Report Message
                System.out.println("ReportMessage = " + taintFlowQuery.getReportMessage());
                //Report Location
                System.out.println("ReportLocation = " + taintFlowQuery.getReportLocation());

                for (TaintFlow taintFlow : taintFlowQuery.getTaintFlows()) {
                    FlowParticipant from = taintFlow.getFrom();
                    List<FlowParticipant> through = taintFlow.getThrough();
                    List<FlowParticipant> notThrough = taintFlow.getNotThrough();
                    FlowParticipant to = taintFlow.getTo();

                    //From
                    if (from instanceof Method) {
                        System.out.println("From = \n" + ((Method) from).getSignature());
                        /*
                        InputDeclaration inputDeclaration = ((Method) from).getInputDeclaration();
                        List<Input> inputs = inputDeclaration.getInputs();

                        for (Input input : inputs) {
                            if (input instanceof Parameter) {
                                ((Parameter) input).getParameterId();
                            } else if (input instanceof ThisObject) {
                                //Todo:
                            }
                        }*/
                    } else {
                        System.out.println("From = \n");
                        for (Method method : ((MethodSet) from).getMethods()) {
                            System.out.println(method.getSignature());
                        }
                    }

                    //Through
                    if (through != null) {
                        if (through.size() > 0) {
                            for (FlowParticipant flowParticipantTemp : through) {
                                if (flowParticipantTemp instanceof Method) {
                                    System.out.println("Through = \n" + ((Method) flowParticipantTemp).getSignature());
                                } else {
                                    System.out.println("Through = \n");
                                    for (Method method : ((MethodSet) flowParticipantTemp).getMethods()) {
                                        System.out.println(method.getSignature());
                                    }
                                }
                            }
                        }
                    }

                    //NotThrough
                    if (notThrough != null) {
                        if (notThrough.size() > 0) {
                            for (FlowParticipant flowParticipantTemp : notThrough) {
                                if (flowParticipantTemp instanceof Method) {
                                    System.out.println("NotThrough = \n" + ((Method) flowParticipantTemp).getSignature());
                                } else {
                                    System.out.println("NotThrough = \n");
                                    for (Method method : ((MethodSet) flowParticipantTemp).getMethods()) {
                                        System.out.println(method.getSignature());
                                    }
                                }
                            }
                        }
                    }

                    if (to instanceof Method) {
                        System.out.println("To = \n" + ((Method) to).getSignature());
                    } else {
                        System.out.println("To = \n");
                        for (Method method : ((MethodSet) to).getMethods()) {
                            System.out.println(method.getSignature());
                        }
                    }
                }
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
            isFirstPageDone = false;
            return false;
        }

        File file = new File(specPath);

        if (file.exists()) {
            if (file.isDirectory()) {
                fluentTQLSpecs.clear();
                fluentTQLSpecs.putAll(InternalFluentTQLIntegration.getSpecs(file.getAbsolutePath()));

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
        return true;
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

                javaFilesAsEntryPoints.add(listOfJavaFiles.get(configurationOption.getName().replace(".java", "")));
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

        for (ConfigurationOption configOption : configuration) {

            if ("FluentTQL Specification's path".equals(configOption.getName())) {
                boolean isSuccess = processFluentTQLSpecificationsPath(configOption);

                if (!isSuccess) {
                    configOption.setValue("");
                    options.clear();
                    options.addAll(configuration);

                    return;
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
     * @param classNames Set of fully qualified class names.
     */
    private void setConfigWithJavaFiles(Set<String> classNames) {
        CheckBox initialOption = new CheckBox(
                "Select java files for entry points",
                "true");

        List<String> sortedClassNames = new ArrayList<>();

        for (String javaFile : classNames) {
            String[] str = javaFile.split("\\.");
            sortedClassNames.add(str[str.length - 1]);

            listOfJavaFiles.put(str[str.length - 1], javaFile);
        }

        Collections.sort(sortedClassNames);

        for (String javaFile : sortedClassNames) {
            javaFilesAsEntryPoints.add(listOfJavaFiles.get(javaFile));
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
