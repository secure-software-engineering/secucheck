package de.fraunhofer.iem.secucheck.cmd;

import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.ErrorModel;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.Errors;
import de.fraunhofer.iem.secucheck.FluentTQLClassLoader.JarClassLoaderUtils;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.DuplicateTaintFlowQueryIDException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import de.fraunhofer.iem.secucheck.JarUtility;
import de.fraunhofer.iem.secucheck.analysis.query.Solver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Checker for the Secucheck configuration settings based on the provided settings yaml file.
 *
 * @author Ranjith Krishnamurthy
 */
public class SecuCheckConfigurationSettingsChecker {
    /**
     * HashMap, contains the FluentTQL specification file name and its corresponding FluentTQLUserInterface object.
     */
    private static final ArrayList<TaintFlowQuery> taintFlowQueries = new ArrayList<>();

    /**
     * Analysis solver. By default its Boomerang3
     */
    private static Solver analysisSolver = Solver.BOOMERANG3;

    /**
     * Set of strings that contains the entry points as method
     */
    private static final HashSet<String> entryPointsAsMethod = new HashSet<>();

    /**
     * Set of FluentTQL Method that represents the General Propagators
     */
    private static final HashSet<Method> generalPropagators = new HashSet<>();

    public static HashSet<String> getEntryPointsAsMethod() {
        return entryPointsAsMethod;
    }

    public static HashSet<Method> getGeneralPropagators() {
        return generalPropagators;
    }

    public static ArrayList<TaintFlowQuery> getTaintFlowQueries() {
        return taintFlowQueries;
    }

    public static Solver getAnalysisSolver() {
        return analysisSolver;
    }

    /**
     * Checks the Secucheck configuration settings file.
     *
     * @param secuCheckConfiguration SecuCheckConfiguration settings
     * @param baseDir                Output directory provided by the user
     */
    public static void check(SecuCheckConfiguration secuCheckConfiguration, String baseDir) throws DuplicateTaintFlowQueryIDException {
        String classPath = secuCheckConfiguration.getClassPath();
        List<String> entryPoints = secuCheckConfiguration.getEntryPoints();
        String specPath = secuCheckConfiguration.getSpecPath();
        List<String> selectedSpecs = secuCheckConfiguration.getSelectedSpecs();
        String solver = secuCheckConfiguration.getSolver();
        boolean isPostProcessResult = secuCheckConfiguration.getIsPostProcessResult();

        // Check for the classPath null or empty
        if (classPath == null || "".equals(classPath.trim())) {
            System.err.println("Class path is not specified. Please use the id \"classPath\" to specify the path of the classes" +
                    "that needs to be analyzed.");
            System.exit(-1);
        }

        // Check for the specPath null or empty
        if (specPath == null || "".equals(specPath.trim())) {
            System.err.println("Specification path is not specified. Please use the id \"specPath\" to specify the path of the " +
                    "fluentTQL specifications.");
            System.exit(-1);
        }

        System.out.println("Verifying the classPath and the entryPoints");
        // Check for the classPath validness
        checkClassPath(classPath, entryPoints, secuCheckConfiguration);

        // Check for the asSpecFile
        if (secuCheckConfiguration.isAsSpecFile()) {
            System.out.println("Given selectedSpecs are Specification files.");
        } else {
            System.out.println("Given selectedSpecs are TaintFlowQueries ID.");
        }

        System.out.println("Verifying the specPath and the selectedSpecs");
        // Check for the specPath validness
        checkSpecPath(specPath, selectedSpecs, baseDir, secuCheckConfiguration);

        System.out.println("Verifying solver");
        // Check for the solver validness
        checkSolver(solver);

        // Check for the isPostProcessResult validness
        checkIsPostProcessResult(isPostProcessResult, solver);
    }

    /**
     * Check for the isPostProcessResult validness.
     * Note: For now, if the isPostProcessResult is enabled for the FlowDroid solver, application exits because
     * Post processing of a result is not avaliable for FlowDroid solver
     *
     * @param isPostProcessResult isPostProcessResult
     * @param solver              Solver
     */
    private static void checkIsPostProcessResult(boolean isPostProcessResult, String solver) {
        if ("flowdroid".equalsIgnoreCase(solver.trim())) {
            if (isPostProcessResult) {
                System.err.println("Post process of a result is not available for FlowDroid solver.\n");
                System.exit(-1);
            }
        }

        if (isPostProcessResult)
            System.out.println("Post processing of the result enabled\n");
        else
            System.out.println("Post processing of the result disabled\n");
    }

    /**
     * Check for the validness of the given solver. If invalid solver is provided then by default it take Boomerang3
     *
     * @param solver Analysis solver
     */
    private static void checkSolver(String solver) {
        if ("boomerang3".equalsIgnoreCase(solver.trim())) {
            analysisSolver = Solver.BOOMERANG3;
        } else if ("flowdroid".equalsIgnoreCase(solver.trim())) {
            analysisSolver = Solver.FLOWDROID;
        } else {
            System.err.println(solver + ": is an invalid solver. Using the default solver (Boomerang3)");
        }
    }

    /**
     * Check for the validness of the given classPath.
     *
     * @param classPath   Class path that needs to be analyzed
     * @param entryPoints Entry points (List of class names)
     */
    private static void checkClassPath(String classPath, List<String> entryPoints, SecuCheckConfiguration secuCheckConfiguration) {
        File file = new File(classPath);
        if (!file.exists()) {
            System.err.println("Given class-path " + classPath + " does not exist.\n" +
                    "Please give a valid path to a directory that contains classes to be analyzed.");
            System.exit(-1);
        }

        if (!file.isDirectory()) {
            System.err.println("Given class-path " + classPath + " is not a directory.\n" +
                    "Please give a valid path to a directory that contains classes to be analyzed.");
            System.exit(-1);
        }

        if (entryPoints == null) {
            try {
                List<String> classes = Files.walk(Paths.get(classPath))
                        .filter(Files::isRegularFile)
                        .map(Path::toAbsolutePath)
                        .map(Path::toString)
                        .filter(a -> a.endsWith(".class"))
                        .map(a -> a.replace(classPath, "").replace(File.separator, ".").replaceAll("^\\.", "").replaceAll("\\.class$", ""))
                        .collect(Collectors.toList());

                secuCheckConfiguration.setEntryPoints(classes);
                entryPoints = classes;
            } catch (IOException e) {
                System.err.println("Something went wrong.\n" + e.getMessage());
                System.exit(-1);
            }
        }

        for (String entryPoint : entryPoints) {
            File temp = new File(file.getAbsolutePath() + File.separator + entryPoint.replace(".", File.separator) + ".class");

            if (!temp.exists()) {
                System.err.println(entryPoint + " is not found in the given class path: " +
                        file.getAbsolutePath());
                System.exit(-1);
            }
        }
    }

    /**
     * Check for the validness of the given specPath.
     *
     * @param specPath      Specification path
     * @param selectedSpecs List of specification selected by the user
     * @param baseDir       Output  directory given by the user
     */
    private static void checkSpecPath(String specPath, List<String> selectedSpecs, String baseDir, SecuCheckConfiguration secuCheckConfiguration) throws DuplicateTaintFlowQueryIDException {
        File file = new File(specPath);

        if (file.exists()) {
            if (file.isDirectory()) {

                File out = new File(file.getAbsolutePath() + System.getProperty("file.separator") + "fluentTQLTemp");

                File[] files = JarUtility.extractJar(file, out);

                if (files.length == 0) {
                    System.err.println("Given Path does not have any Jar files.\n" +
                            "FluentTQL scans upto:\n" +
                            "Max sub-directories in current dir = 20\n" +
                            "Max depth of sub-directories = 20");

                    JarUtility.deleteDir(out);
                    System.exit(-1);
                }

                taintFlowQueries.clear();

                JarClassLoaderUtils jarClassLoaderUtils = new JarClassLoaderUtils();

                HashMap<String, FluentTQLUserInterface> specs = jarClassLoaderUtils.loadAppAndGetFluentTQLSpecification(out.getAbsolutePath());

                HashMap<String, TaintFlowQuery> specWithID = storeCompleteTaintFlowQueries(specs);

                if (selectedSpecs == null) {
                    if (secuCheckConfiguration.isAsSpecFile()) {
                        selectedSpecs = new ArrayList<>(specs.keySet());
                    } else {
                        selectedSpecs = new ArrayList<>(specWithID.keySet());
                    }

                    secuCheckConfiguration.setSelectedSpecs(selectedSpecs);
                }

                for (String spec : selectedSpecs) {
                    if (secuCheckConfiguration.isAsSpecFile()) {
                        if (!specs.containsKey(spec)) {
                            System.err.println(spec + " is not found in the given FluentTQL specifications (Path: " +
                                    file.getAbsolutePath() + ")");
                            System.exit(-1);
                        } else {
                            addTaintFLowQueries(specs.get(spec).getFluentTQLSpecification());
                        }
                    } else {
                        if (!specWithID.containsKey(spec)) {
                            System.err.println(spec + " is not found in the given FluentTQL specifications (Path: " +
                                    file.getAbsolutePath() + ")");
                            System.exit(-1);
                        } else {
                            taintFlowQueries.add(specWithID.get(spec));
                        }
                    }
                }

                JarUtility.deleteDir(out);

                createErrorText(jarClassLoaderUtils.getErrorModel(), baseDir);
                entryPointsAsMethod.addAll(jarClassLoaderUtils.getEntryPoints());
                generalPropagators.addAll(jarClassLoaderUtils.getGeneralPropagators());

                if (taintFlowQueries.size() <= 0) {
                    System.err.println("No FluentTQL specifications present in the given path!!!");
                    System.exit(-1);
                }
            } else {
                System.err.println("Given FluentTQL Specification's path is not a directory!!! \nPlease give valid directory name.");
                System.exit(-1);
            }
        } else {
            System.err.println("Given FluentTQL Specification's path does not exist!!!");
            System.exit(-1);
        }
    }

    private static HashMap<String, TaintFlowQuery> storeCompleteTaintFlowQueries(HashMap<String, FluentTQLUserInterface> fluentTQLSpecs) {
        HashMap<String, TaintFlowQuery> completeTaintFlowQueries = new HashMap<>();

        // Get the TaintFLowQuery object.
        for (String fluentSpecFileName : fluentTQLSpecs.keySet()) {
            FluentTQLUserInterface fluentTQLUserInterface = fluentTQLSpecs.get(fluentSpecFileName);

            for (FluentTQLSpecification fluentTQLSpecification : fluentTQLUserInterface.getFluentTQLSpecification()) {
                if (fluentTQLSpecification instanceof TaintFlowQuery) {
                    TaintFlowQuery taintFlowQuery = (TaintFlowQuery) fluentTQLSpecification;

                    completeTaintFlowQueries.put(taintFlowQuery.getId(), taintFlowQuery);
                } else if (fluentTQLSpecification instanceof QueriesSet) {
                    QueriesSet queriesSet = (QueriesSet) fluentTQLSpecification;

                    for (TaintFlowQuery taintFlowQuery : queriesSet.getTaintFlowQueries()) {
                        completeTaintFlowQueries.put(taintFlowQuery.getId(), taintFlowQuery);
                    }
                }
            }
        }

        return completeTaintFlowQueries;
    }

    /**
     * This method creates a FluentTQLError.txt with the errors found while processing the FluentTQL specifications
     *
     * @param errors Errors
     */
    private static void createErrorText(Errors errors, String baseDir) {
        if (errors.getErrors().size() > 0) {
            File file = new File(baseDir + File.separator + "FluentTQLError.txt");

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
            System.err.println("There some errors while processing some specifications. Please check the below file for more details.\n" +
                    file.getAbsolutePath().toString());
        }
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
}
