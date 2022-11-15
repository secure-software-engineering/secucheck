package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.AnalysisEntryPoint;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.*;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * This class processes the FluentTQL {@link AnalysisEntryPoint} annotations and givens the list of all the method signature annotated as {@link AnalysisEntryPoint}.
 *
 * @author Ranjith Krishnamurthy
 */
public class ProcessAnalysisEntryPointAnnotation {
    /**
     * Process the given object and returns list of method signature that is annotated as AnalysisEntryPoint
     *
     * @param fluentTQLRelatedObject FluentTQL related class object
     * @return Sets of method signature annotated as AnalysisEntryPoint
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     */
    public HashSet<String> getEntryPoints(Object fluentTQLRelatedObject) throws MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, FieldNotPublicException, FieldNullPointerException {
        new ProcessAnnotatedClass().isValidFluentTQLRelatedClass(fluentTQLRelatedObject);

        HashSet<String> entryPoints = new HashSet<>();

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
     * @return Sets of method signature
     * @throws FieldNotPublicException   If the annotated field is not in public modifier.
     * @throws FieldNullPointerException If the annotated field is not initialized while declaring it.
     */
    private HashSet<String> getMethodSignatureIfEntryPoint(Field field, Object fluentTQLRelatedObject) throws FieldNullPointerException, FieldNotPublicException {
        try {
            if (!field.isAnnotationPresent(AnalysisEntryPoint.class))
                return new HashSet<String>();

            if (!field.getType().equals(Method.class) && !field.getType().equals(MethodSet.class))
                return new HashSet<String>();

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

                HashSet<String> entryPoints = new HashSet<>();
                entryPoints.add(methodObj.getSignature());

                return entryPoints;
            }

            MethodSet methodSet = (MethodSet) obj;

            HashSet<String> entryPoints = new HashSet<>();
            for (Method method : methodSet.getMethods()) {
                entryPoints.add(method.getSignature());
            }

            return entryPoints;

        } catch (IllegalAccessException e) {
            throw new FieldNotPublicException(field.getName(), fluentTQLRelatedObject.getClass().getSimpleName());
        }
    }
}
