package de.fraunhofer.iem.secucheck.cmd;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisConfiguration;
import de.fraunhofer.iem.secucheck.analysis.configuration.SecucheckAnalysisDefaultConfiguration;
import de.fraunhofer.iem.secucheck.analysis.query.EntryPoint;
import de.fraunhofer.iem.secucheck.analysis.query.OS;
import de.fraunhofer.iem.secucheck.analysis.query.Solver;
import de.fraunhofer.iem.secucheck.analysis.result.AnalysisResultListener;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintAnalysisResult;
import de.fraunhofer.iem.secucheck.analysis.result.SecucheckTaintFlowQueryResult;
import de.fraunhofer.iem.secucheck.analysis.result.TaintFlowResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Configures the SecuchAnalysis from the given SecuCheck configuration settings
 *
 * @author Ranjith Krishnamurthy
 */
public class SecuCheckAnalysisConfigurator {
    /**
     * Secucheck configuration settings provided by user through yaml file
     */
    private final SecuCheckConfiguration secuCheckConfiguration;

    public SecuCheckAnalysisConfigurator(SecuCheckConfiguration secuCheckConfiguration) {
        this.secuCheckConfiguration = secuCheckConfiguration;
    }

    /**
     * Configures the Secucheck analysis and run the analysis
     *
     * @param taintFlowQueries List of Taintflow queries
     * @param analysisSolver   Analysis solver
     * @param operatingSystem  Operatin system
     */
    public void run(List<TaintFlowQuery> taintFlowQueries, Solver analysisSolver, OS operatingSystem) {

        try {
            try {
                SecucheckAnalysisConfiguration configuration = getAnalysisConfiguration(analysisSolver, operatingSystem);
                SecuCheckAnalysisWrapper secucheckAnalysis = new SecuCheckAnalysisWrapper(configuration);

                //List<CompositeTaintFlowQueryImpl> compositeQueries = FluentTQLUtility.getCompositeTaintFlowQueries(taintFlowQueries);
                SecucheckTaintAnalysisResult result = secucheckAnalysis.run(taintFlowQueries);
                System.out.println("\n\n\nCheck critical results = " + result.size() + "\n\n\n");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the Soot class path
     *
     * @return Soot class path
     */
    private static String getSootClassPath() {
        String rtJarPath = System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar";
        if (new File(rtJarPath).exists()) {
            return rtJarPath;
        } else {
            return "";
        }
    }

    /**
     * Creates EntryPoint from the give list of entry points from the user through settings
     *
     * @return EntryPoints
     */
    private List<EntryPoint> getEntryPoints() {
        List<String> typeNames = secuCheckConfiguration.getEntryPoints();

        List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();

        for (String typeName : typeNames) {
            EntryPoint entryPoint = new EntryPoint();
            entryPoint.setCanonicalClassName(typeName);
            entryPoint.setAllMethods(true);
            entryPoints.add(entryPoint);
        }

        return entryPoints;
    }

    /**
     * Configures the SecucheckAnalysis based on the settings provided by the user
     *
     * @param solver Analysis solver
     * @param os     Operating system
     * @return SecucheckAnalysisConfiguration
     */
    private SecucheckAnalysisConfiguration getAnalysisConfiguration(Solver solver, OS os) {
        AnalysisResultListener resultListener = getConsoleResultListener();
        SecucheckAnalysisConfiguration configuration = new SecucheckAnalysisDefaultConfiguration();
        configuration.setOs(os);
        configuration.setSolver(solver);
        configuration.setAnalysisEntryPoints(getEntryPoints());
        configuration.setApplicationClassPath(secuCheckConfiguration.getClassPath());
        configuration.setSootClassPathJars(getSootClassPath());
        configuration.setListener(resultListener);
        configuration.setAnalysisGeneralPropagators(GeneralPropagators.getGP());
        return configuration;
    }

    /**
     * Returns the default implementation of the AnalysisResultListener
     *
     * @return AnalysisResultListener
     */
    private static AnalysisResultListener getConsoleResultListener() {
        return new AnalysisResultListener() {
            @Override
            public boolean isCancelled() {
                return false;
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
