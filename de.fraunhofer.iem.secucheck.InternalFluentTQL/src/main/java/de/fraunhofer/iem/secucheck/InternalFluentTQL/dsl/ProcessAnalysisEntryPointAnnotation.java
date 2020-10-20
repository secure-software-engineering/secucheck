package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.AnalysisEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class processes the FluentTQL AnalysisEntryPoint annotations and givens the list of all the method signature annotated as AnalysisEntryPoint.
 *
 * @author Ranjith Krishnamurthy
 */
public class ProcessAnalysisEntryPointAnnotation {
    /**
     * Process the given object and returns list of method signature that is annotated as AnalysisEntryPoint
     *
     * @param fluentTQLRelatedObject FluentTQL related class object
     * @return List of method signature annotated as AnalysisEntryPoint
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     */
    public List<String> getEntryPoints(Object fluentTQLRelatedObject) throws MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, FieldNotPublicException, FieldNullPointerException {
        new ProcessAnnotatedClass().isValidFluentTQLRelatedClass(fluentTQLRelatedObject);

        ArrayList<String> entryPoints = new ArrayList<>();

        for (Field field : fluentTQLRelatedObject.getClass().getDeclaredFields()) {
            entryPoints.addAll(getMethodSignatureIfEntryPoint(field, fluentTQLRelatedObject));
        }

        Set<String> uniqueMethodSignature = new HashSet<>(entryPoints);

        entryPoints.clear();
        entryPoints.addAll(uniqueMethodSignature);

        return entryPoints;
    }

    /**
     * This method process a single field and returns the list of method signature if it is annotated as AnalysisEntryPoint
     *
     * @param field                  Field
     * @param fluentTQLRelatedObject FluentTQL related class object
     * @return List of method signature
     * @throws FieldNotPublicException   If the annotated field is not in public modifier.
     * @throws FieldNullPointerException If the annotated field is not initialized while declaring it.
     */
    private List<String> getMethodSignatureIfEntryPoint(Field field, Object fluentTQLRelatedObject) throws FieldNullPointerException, FieldNotPublicException {
        try {
            if (!field.isAnnotationPresent(AnalysisEntryPoint.class))
                return new ArrayList<String>();

            if (!field.getType().equals(Method.class) && !field.getType().equals(MethodSet.class))
                return new ArrayList<String>();

            Object obj;

            if (Modifier.isStatic(field.getModifiers()))
                obj = field.get(fluentTQLRelatedObject.getClass());
            else
                obj = field.get(fluentTQLRelatedObject);

            if (obj == null) {
                throw new FieldNullPointerException(field.getName(), fluentTQLRelatedObject.getClass().getSimpleName());
            }

            if (field.getType().equals(Method.class)) {
                Method methodObj = (Method) obj;

                List<String> entryPoints = new ArrayList<>();
                entryPoints.add(methodObj.getSignature());

                return entryPoints;
            }

            MethodSet methodSet = (MethodSet) obj;

            List<String> entryPoints = new ArrayList<>();
            for (Method method : methodSet.getMethods()) {
                entryPoints.add(method.getSignature());
            }

            return entryPoints;

        } catch (IllegalAccessException e) {
            throw new FieldNotPublicException(field.getName(), fluentTQLRelatedObject.getClass().getSimpleName());
        }
    }
}
