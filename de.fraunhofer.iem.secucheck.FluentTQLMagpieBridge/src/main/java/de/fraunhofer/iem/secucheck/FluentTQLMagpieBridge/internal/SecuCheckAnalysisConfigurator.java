package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.internal;

import com.ibm.wala.classLoader.Module;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLAnalysisConfigurator;
import de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLMagpieBridgeMainServer;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.SecucheckAnalysisDefaultConfiguration;
import de.fraunhofer.iem.secucheck.analysis.SecucheckTaintAnalysis;
import de.fraunhofer.iem.secucheck.analysis.query.CompositeTaintFlowQueryImpl;
import de.fraunhofer.iem.secucheck.analysis.query.EntryPoint;
import de.fraunhofer.iem.secucheck.analysis.query.OS;
import de.fraunhofer.iem.secucheck.analysis.query.Solver;
import de.fraunhofer.iem.secucheck.analysis.result.AnalysisResultListener;
import de.fraunhofer.iem.secucheck.analysis.result.CompositeTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowQueryResult;
import magpiebridge.core.AnalysisConsumer;
import magpiebridge.core.AnalysisResult;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
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
            lastAnalysisTask.cancel(false);
            if (lastAnalysisTask.isCancelled()) {
                logger.log(Level.INFO, "Last running analysis has been cancelled.");
            }
        }
    }

    public static void run(List<TaintFlowQuery> taintFlowQueries) {
        cancel();

        // Perform validation synchronously and run analysis asynchronously.
        if (FluentTQLAnalysisConfigurator.validateQueriesAndEntryPoints()) {
            Runnable analysisTask = () -> {
                try {
                    try {
                        SecucheckTaintAnalysis secucheckAnalysis = new SecucheckTaintAnalysis();
                        SecucheckAnalysisConfiguration configuration = getAnalysisConfiguration(Solver.BOOMERANG3, OS.WINDOWS);
                        secucheckAnalysis.setConfiguration(configuration);
                        List<CompositeTaintFlowQueryImpl> compositeQueries = FluentTQLUtility.getCompositeTaintFlowQueries(taintFlowQueries);
                        SecucheckTaintAnalysisResult result = secucheckAnalysis.run(compositeQueries);
                        System.out.println("Critical Result = " + result.size());

                        //server.consume(results, "secucheck-analysis");
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
        return System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar";
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
        return configuration;
    }

    private static AnalysisResultListener getConsoleResultListener() {
        return new AnalysisResultListener() {
            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public void reportFlowResult(TaintFlowQueryResult result) {
            }

            @Override
            public void reportCompositeFlowResult(CompositeTaintFlowQueryResult result) {
            }

            @Override
            public void reportCompleteResult(SecucheckTaintAnalysisResult result) {
                System.out.println();
                System.out.println("Recieved complete result, size:" + result.size());
            }
        };
    }
}
