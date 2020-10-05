package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLCompiler;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import java.util.HashMap;

public class JarClassLoaderUtils {
    private final ErrorModel errorModel = new ErrorModel();
    private static final HashMap<String, FluentTQLUserInterface> fluentTQLSpecs = new HashMap<>();

    public ErrorModel getErrorModel() {
        return errorModel;
    }

    public HashMap<String, FluentTQLUserInterface> loadAppAndGetFluentTQLSpecification(String path) {
        errorModel.getError().clear();

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
                        errorModel.addNewError(loadedResourcesKey.toString(), "Do not keep the class in default package");
                        continue;
                    }

                    jarClassLoader.loadClass(className);
                } catch (Exception ex) {
                    //Todo:
                    errorModel.addNewError(loadedResourcesKey.toString(), ex.getMessage());
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
                //Todo:
                errorModel.addNewError(loadedClassesKey.toString(), ex.getMessage());
            }
        }
        return fluentTQLSpecs;
    }

    private void processFluentTQLAnnotation(Object obj) {
        try {
            if (obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) {
                FluentTQLUserInterface fluentTQLUserInterface = ProcessAnnotatedClass.processFluentTQLSpecificationClassAnnotation(obj);
                fluentTQLSpecs.put(obj.getClass().getSimpleName(), fluentTQLUserInterface);
            } else {
                ProcessAnnotatedClass.processFluentTQLAnnotation(obj);
            }
        } catch (NotAFluentTQLSpecificationException | DoesNotImplementFluentTQLUserInterfaceException | ImportAndProcessAnnotationException | FieldNullPointerException | IncompleteMethodDeclarationException ex) {
            errorModel.addNewError(obj.getClass().getName(), ex.getMessage());
        }
    }
}
