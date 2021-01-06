package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge;

import com.ibm.wala.classLoader.Module;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.ErrorModel;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.Errors;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.JarClassLoaderUtils;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.utility.JarUtility;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.SecucheckHttpServer.utility.PrintUtility;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal.SecuCheckAnalysisWrapper;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal.SecucheckMagpieBridgeAnalysis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
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

    private static final String RECOMPILE_CONFIG_CONS = "Re-Compile fluentTQL specifications";
    private static final String ENTRY_POINTS_CONFIG_CONS = "Select java files for entry points:";

    private static final HashSet<String> entryPointsAsMethod = new HashSet<>();
    private static final HashSet<String> entryPoints = new HashSet<>();
    private static final HashSet<Method> generalPropagators = new HashSet<>();
    private static final Set<Path> classPath = new HashSet<>();
    public static final Set<Path> sourcePath = new HashSet<>();
    private static final Set<Path> libraryPath = new HashSet<>();
    private static final List<TaintFlowQuery> taintFlowQueries = new ArrayList<>();

    private static final List<ConfigurationOption> currentConfiguration = new ArrayList<>();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    private static final Path projectRootPath = null;

    private final List<ConfigurationOption> options = new ArrayList<>();
    private final HashMap<String, String> listOfJavaFiles = new HashMap<>();
    private final SecucheckMagpieBridgeAnalysis secucheckAnalysis = new SecuCheckAnalysisWrapper(true);

    private boolean isFirstPageDone = false;
    private Future<?> lastAnalysisTask;
    private final ExecutorService execService;

    /**
     * Constructor sets the initial configuration option for the configuration page.
     */
    public FluentTQLAnalysis() {
        currentConfiguration.addAll(options);
        execService = Executors.newSingleThreadExecutor();
    }

    /**
     * Returns the source name for the Magpie bridge required for configuration page.
     *
     * @return Source name
     */
    public String source() {

        return "";
    }

    /**
     * This method runs the analysis
     *
     * @param files  Files
     * @param server Analysis Server
     * @param rerun  Is rerun
     */
    public void analyze(Collection<? extends Module> files, AnalysisConsumer server, boolean rerun) {
        if (rerun) {
            if (lastAnalysisTask != null && !lastAnalysisTask.isDone()) {
                lastAnalysisTask.cancel(false);
                if (lastAnalysisTask.isCancelled()) {
                    logger.log(Level.INFO, "Last running analysis has been cancelled.");
                }
            }

            //Todo: Verify whether is it required to remove the bin directories from the class path.
            Set<Path> modifiedClassPath = new HashSet<Path>();
            for (Path clsPath : classPath) {
                if (!clsPath.toAbsolutePath().toString().contains("bin")) {
                    modifiedClassPath.add(clsPath);
                }
            }

            // Perform validation synchronously and run analysis asynchronously.
            if (validateQueriesAndEntryPoints()) {
                System.out.println("\n\n\n************************************************");
                System.out.println("\nEntry points as class size = " + entryPoints.size());
                System.out.println("Entry points as method size = " + entryPointsAsMethod.size());
                System.out.println("TaintFlow queries size = " + taintFlowQueries.size());
                System.out.println("General Propagator size = " + generalPropagators.size());

                System.out.println("\n\nEntry Points as Method\n");
                for (String entryPoint : entryPointsAsMethod) {
                    System.out.println(entryPoint);
                }

                System.out.println("\n\nGeneral Propagators\n");
                for (Method generalPropagator : generalPropagators) {
                    System.out.println(generalPropagator.toString());
                }
                System.out.println("\n\n************************************************\n\n\n");
                Runnable analysisTask = () -> {
                    try {
                        Collection<AnalysisResult> results = secucheckAnalysis.run(taintFlowQueries,
                                new ArrayList<>(entryPoints), modifiedClassPath, libraryPath, projectRootPath.toAbsolutePath().toString());

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
     * This method configures the analysis or configuration page based on the user input given in the previous
     * configuration page.
     *
     * @param configuration List of configuration options from the Magpie bridge
     */
    public void configure(List<ConfigurationOption> configuration) {
        currentConfiguration.clear();
        currentConfiguration.addAll(configuration);
    }





    public String[] getCommand() {
        return new String[0];
    }

    public Collection<AnalysisResult> convertToolOutput() {
        return null;
    }
}
