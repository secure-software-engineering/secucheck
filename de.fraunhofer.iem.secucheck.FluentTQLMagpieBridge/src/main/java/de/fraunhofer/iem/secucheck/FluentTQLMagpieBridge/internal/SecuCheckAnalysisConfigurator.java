package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLMagpieBridgeMainServer;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.SecuCheckCoreQueryUtility;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisDefaultConfiguration;
import de.fraunhofer.iem.secucheck.analysis.query.EntryPoint;
import de.fraunhofer.iem.secucheck.analysis.query.MethodImpl;
import de.fraunhofer.iem.secucheck.analysis.query.OS;
import de.fraunhofer.iem.secucheck.analysis.query.Solver;
import de.fraunhofer.iem.secucheck.analysis.result.AnalysisResultListener;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowResult;
import magpiebridge.core.AnalysisResult;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import com.sun.jna.Platform;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecuCheckAnalysisConfigurator {
    private static final Logger logger = Logger.getLogger("SecuCheck Analysis Configurator");
    private static Future<?> lastAnalysisTask;
    private static final ExecutorService execService = Executors.newSingleThreadExecutor();

    public static void cancel() {
        if (lastAnalysisTask != null && !lastAnalysisTask.isDone()) {
            lastAnalysisTask.cancel(true);
            if (lastAnalysisTask.isCancelled()) {
                logger.log(Level.INFO, "Last running analysis has been cancelled.");
            }
        }
    }

    public static void run(List<TaintFlowQuery> taintFlowQueries, Solver analysisSolver) {
        cancel();

        // Perform validation synchronously and run analysis asynchronously.
        if (FluentTQLAnalysisConfigurator.validateQueriesAndEntryPoints()) {
            Runnable analysisTask = () -> {
                try {
                    try {
                        OS operatingSystem;

                        if (Platform.isWindows())
                            operatingSystem = OS.WINDOWS;
                        else if (Platform.isLinux())
                            operatingSystem = OS.LINUX;
                        else if (Platform.isMac())
                            operatingSystem = OS.MACOS;
                        else
                            operatingSystem = OS.OTHER;

                        SecucheckAnalysisConfiguration configuration = getAnalysisConfiguration(analysisSolver, operatingSystem);
                        SecuCheckAnalysisWrapper secucheckAnalysis = new SecuCheckAnalysisWrapper(configuration);

                        //List<CompositeTaintFlowQueryImpl> compositeQueries = FluentTQLUtility.getCompositeTaintFlowQueries(taintFlowQueries);
                        Collection<AnalysisResult> result = secucheckAnalysis.run(taintFlowQueries);
                        System.out.println("\n\n\nCheck critical results = " + result.size() + "\n\n\n");

                        //Collection<AnalysisResult> results = Utility.getMagpieBridgeResult(result);

                        //System.out.println("\n\n\nSecondCheck critical results = " + results.size() + "\n\n\n");

                        if (!Thread.currentThread().isInterrupted()) {
                            System.out.println("\n\n\nCritical Result = " + result.size());
                            FluentTQLMagpieBridgeMainServer.fluentTQLMagpieServer.cleanUp();
                            FluentTQLMagpieBridgeMainServer.fluentTQLMagpieServer.consume(result, "secucheck-analysis");
                        } else {
                            System.out.println("\n\n\nInterrupted = " + result.size() + "\n\n\n");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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


    private static String getSootClassPath() {
        String rtJarPath = System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar";
        if (new File(rtJarPath).exists()) {
            return rtJarPath;
        } else {
            return "";
        }
    }

    private static List<EntryPoint> getEntryPoints() {
        List<String> typeNames = new ArrayList<String>(FluentTQLAnalysisConfigurator.getEntryPoints());

        List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();

        for (String typeName : typeNames) {
            // For WebGoat Server
            EntryPoint entryPoint = new EntryPoint();
            entryPoint.setCanonicalClassName(typeName);
            entryPoint.setAllMethods(true);
            entryPoints.add(entryPoint);
        }

        return entryPoints;
    }

    private static SecucheckAnalysisConfiguration getAnalysisConfiguration(Solver solver, OS os) {
        AnalysisResultListener resultListener = getConsoleResultListener();
        SecucheckAnalysisConfiguration configuration = new SecucheckAnalysisDefaultConfiguration();
        configuration.setOs(os);
        configuration.setSolver(solver);
        configuration.setAnalysisEntryPoints(getEntryPoints());
        configuration.setApplicationClassPath(FluentTQLAnalysisConfigurator.getClassPathAsString());
        configuration.setSootClassPathJars(getSootClassPath());
        configuration.setListener(resultListener);

        List<MethodImpl> generalPropagators = new ArrayList<>();

        for (Method method : FluentTQLAnalysisConfigurator.getGeneralPropagators()) {
            generalPropagators.add(SecuCheckCoreQueryUtility.getMethodImpl(method));
        }

        configuration.setAnalysisGeneralPropagators(generalPropagators);
        return configuration;
    }

    private static AnalysisResultListener getConsoleResultListener() {
        return new AnalysisResultListener() {
            @Override
            public boolean isCancelled() {
                if (lastAnalysisTask != null) {
                    return lastAnalysisTask.isCancelled() || lastAnalysisTask.isDone();
                }

                return true;
            }

            @Override
            public void reportCompleteResult(SecucheckTaintAnalysisResult result) {
            }

            @Override
            public void reportSecucheckTaintFlowQueryResult(SecucheckTaintFlowQueryResult
                                                                    secucheckTaintFlowQueryResult) {
            }

            @Override
            public void reportTaintFlowResult(TaintFlowResult taintFlowResult) {
            }
        };
    }
}
