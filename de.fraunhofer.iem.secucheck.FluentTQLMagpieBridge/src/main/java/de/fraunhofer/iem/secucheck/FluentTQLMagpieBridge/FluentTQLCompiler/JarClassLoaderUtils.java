package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLCompiler;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

public class JarClassLoaderUtils {
    private final Errors errors = new Errors();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    public Errors getErrorModel() {
        return errors;
    }

    public HashMap<String, FluentTQLUserInterface> loadAppAndGetFluentTQLSpecification(String path) {
        errors.getErrors().clear();
        fluentTQLSpecs.clear();

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

                processFluentTQLAnnotation(obj);
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                errors.addError(
                        new ErrorModel(errors.getErrors().size() + 1, loadedClassesKey.toString(), ex.getMessage(), sw.toString())
                );
            }
        }
        return fluentTQLSpecs;
    }

    private void processFluentTQLAnnotation(Object obj) {
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();

        try {
            if (obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) {
                FluentTQLUserInterface fluentTQLUserInterface = processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(obj);
                fluentTQLSpecs.put(obj.getClass().getSimpleName(), fluentTQLUserInterface);
            } else {
                processAnnotatedClass.processFluentTQLAnnotation(obj);
            }
        } catch (FluentTQLException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            errors.addError(
                    new ErrorModel(errors.getErrors().size() + 1, obj.getClass().getName(), ex.getMessage(), sw.toString())
            );
        }
    }
}
