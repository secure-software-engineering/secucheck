package de.fraunhofer.iem.secucheck.FluentTQLClassLoader;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnalysisEntryPointAnnotation;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * This utility class provides the feature of loading the FluentTQL related class using the JarClassLoader.
 *
 * @author Ranjith Krishnamurthy
 */
public class JarClassLoaderUtils {
    private final Errors errors = new Errors();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();
    private final PrintUtils printUtils = new PrintUtils();
    private final HashSet<String> entryPoints = new HashSet<>();

    /**
     * This method returns the list of method signature annotated as AnalysisEntryPoints that is process in the previous call of loadAppAndGetFluentTQLSpecification
     *
     * @return List of Method signature annotated as AnalysisEntryPoints
     */
    public HashSet<String> getEntryPoints() {
        return entryPoints;
    }

    /**
     * Returns the Errors that occurred in the previous run of loadAppAndGetFluentTQLSpecification
     *
     * @return Errors
     */
    public Errors getErrorModel() {
        return errors;
    }

    /**
     * This method tries to load the given Jar path resource and tries to loads the class in that Jar. If any errors it records in the Errors.
     * By default PrettyPrint is disabled.
     *
     * @param path FluentTQL specifications Jar path
     * @return HashMap of class name and its corresponding FluentTQLUserInterface
     */
    public HashMap<String, FluentTQLUserInterface> loadAppAndGetFluentTQLSpecification(String path) {
        return loadAppAndGetFluentTQLSpecification(path, false);
    }

    /**
     * This method tries to load the given Jar path resource and tries to loads the class in that Jar. If any errors it records in the Errors.
     *
     * @param path          FluentTQL specifications Jar path
     * @param isPrettyPrint Pretty Print the Status of each Class
     * @return HashMap of class name and its corresponding FluentTQLUserInterface
     */
    public HashMap<String, FluentTQLUserInterface> loadAppAndGetFluentTQLSpecification(String path, boolean isPrettyPrint) {
        errors.getErrors().clear();
        fluentTQLSpecs.clear();
        entryPoints.clear();

        JarClassLoader jarClassLoader = new JarClassLoader();
        jarClassLoader.add(path);

        Object[] loadedResourcesKeys = jarClassLoader.getLoadedResources().keySet().toArray();

        for (Object loadedResourcesKey : loadedResourcesKeys) {
            if (loadedResourcesKey.toString().endsWith(".class")) {
                try {
                    String className = loadedResourcesKey.toString()
                            .replaceAll("/", "\\.")
                            .replace(".class", "");

                    if (className.startsWith(".")) {
                        errors.addError(
                                new ErrorModel(errors.getErrors().size() + 1, loadedResourcesKey.toString(), "Do not keep the class in default package")
                        );

                        if (isPrettyPrint)
                            printUtils.printClassStatus(
                                    loadedResourcesKey.toString().split("/")[loadedResourcesKey.toString().split("/").length - 1],
                                    "Failed",
                                    true);

                        continue;
                    }

                    jarClassLoader.loadClass(className);
                } catch (Exception | Error ex) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    errors.addError(
                            new ErrorModel(errors.getErrors().size() + 1, loadedResourcesKey.toString(), ex.getMessage(), sw.toString())
                    );

                    if (isPrettyPrint)
                        printUtils.printClassStatus(
                                loadedResourcesKey.toString().split("/")[loadedResourcesKey.toString().split("/").length - 1],
                                "Failed",
                                true);
                }
            }
        }

        JclObjectFactory jclObjectFactory = JclObjectFactory.getInstance();

        Object[] loadedClassesKeys = jarClassLoader.getLoadedClasses().keySet().toArray();

        for (Object loadedClassesKey : loadedClassesKeys) {
            try {
                Object obj = jclObjectFactory.create(
                        jarClassLoader, loadedClassesKey.toString().replaceAll("/", "\\.").replace(".class", "")
                );

                processFluentTQLAnnotation(obj, isPrettyPrint);
            } catch (Exception | Error ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                errors.addError(
                        new ErrorModel(errors.getErrors().size() + 1, loadedClassesKey.toString(), ex.getMessage(), sw.toString())
                );

                if (isPrettyPrint)
                    printUtils.printClassStatus(
                            loadedClassesKey.toString().split("\\.")[loadedClassesKey.toString().split("\\.").length - 1],
                            "Failed",
                            true);
            }
        }
        return fluentTQLSpecs;
    }

    /**
     * This method process the FluentTQL related classes
     *
     * @param obj FluentTQL related Object
     */
    private void processFluentTQLAnnotation(Object obj, boolean isPrettyPrint) {
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        ProcessAnalysisEntryPointAnnotation processAnalysisEntryPointAnnotation = new ProcessAnalysisEntryPointAnnotation();

        try {
            if (obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) {
                FluentTQLUserInterface fluentTQLUserInterface = processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(obj);
                fluentTQLSpecs.put(obj.getClass().getSimpleName(), fluentTQLUserInterface);
            } else {
                processAnnotatedClass.processFluentTQLAnnotation(obj);
            }

            entryPoints.addAll(processAnalysisEntryPointAnnotation.getEntryPoints(obj));

            if (isPrettyPrint)
                printUtils.printClassStatus(
                        obj.getClass().getSimpleName(),
                        "Verified",
                        false);

        } catch (FluentTQLException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            errors.addError(
                    new ErrorModel(errors.getErrors().size() + 1, obj.getClass().getName(), ex.getMessage(), sw.toString())
            );

            if (isPrettyPrint)
                printUtils.printClassStatus(obj.getClass().getSimpleName(), "Failed", true);
        }
    }
}
