package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * This class processes the FluentTQL related annotations and configure the Specification.
 * Use this class to get the processed and complete FluentTQL TaintFLow specifications.
 *
 * @author Ranjith Krishnamurthy
 */
public class ProcessAnnotatedClass {
    /**
     * This checks whether the given Object is a valid FluentTQL related class.
     *
     * @param obj Object
     * @return Boolean: True if it is valid FluentTQL related class.
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     */
    private static boolean isValidFluentTQLRelatedClass(Object obj) throws DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, MissingFluentTQLSpecificationClassAnnotationException {
        if (!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class) &&
                !obj.getClass().isAnnotationPresent(FluentTQLRepositoryClass.class)) {
            throw new NotAFluentTQLRelatedClassException(obj.getClass().getName());
        } else {
            if (!(obj instanceof FluentTQLUserInterface)) {
                if (obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class))
                    throw new DoesNotImplementFluentTQLUserInterfaceException(obj.getClass().getName());
            } else {
                if (!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class))
                    throw new MissingFluentTQLSpecificationClassAnnotationException(obj.getClass().getName());
            }
        }
        return true;
    }

    /**
     * This method tries to process the given object and get the FluentTQL Specification class i.e. FluentTQLUserInterface. Object has to be annotated with FluentTQLSpecificationClass and
     * must implement FluentTQLUserInterface.
     *
     * @param fluentTQLSpec Object
     * @return FluentTQLUserInterface
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     */
    public static FluentTQLUserInterface processFluentTQLSpecificationClassAnnotation(Object fluentTQLSpec) throws DoesNotImplementFluentTQLUserInterfaceException, ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, NotAFluentTQLRelatedClassException, MissingFluentTQLSpecificationClassAnnotationException, NotFoundZeroArgumentConstructorException {
        isValidFluentTQLRelatedClass(fluentTQLSpec);
        return (FluentTQLUserInterface) ProcessAnnotatedClass.processFluentTQLAnnotation(fluentTQLSpec);
    }

    /**
     * This class processes the given object for the FluentTQL related annotation and configures the methods and returns the Object. This can also be a FluentTQLRepositoryClass.
     *
     * @param fluentTQLRelatedClass Object
     * @return Processed Object
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     */
    public static Object processFluentTQLAnnotation(Object fluentTQLRelatedClass) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, NotFoundZeroArgumentConstructorException {
        isValidFluentTQLRelatedClass(fluentTQLRelatedClass);

        if (fluentTQLRelatedClass.getClass().isAnnotationPresent(ImportAndProcessOnlyStaticFields.class)) {
            importAndProcessStaticOnly(fluentTQLRelatedClass);
        }

        processEachField(fluentTQLRelatedClass, false);
        return fluentTQLRelatedClass;
    }

    /**
     * This method processes field's annotation of a single object recursively and configures it accordingly.
     *
     * @param fluentTQLSpec       Object
     * @param isProcessOnlyStatic whether process only static field or all field
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     */
    private static void processEachField(Object fluentTQLSpec, boolean isProcessOnlyStatic) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, NotFoundZeroArgumentConstructorException {
        for (Field field : fluentTQLSpec.getClass().getDeclaredFields()) {
            if (isProcessOnlyStatic) {
                if (Modifier.isStatic(field.getModifiers())) {
                    processSingleField(field, fluentTQLSpec, Modifier.isStatic(field.getModifiers()));
                }
            } else {
                processSingleField(field, fluentTQLSpec, Modifier.isStatic(field.getModifiers()));
            }
        }
    }

    /**
     * This method processes a single field' annotations.
     *
     * @param field         Field
     * @param fluentTQLSpec Object that the field belongs to
     * @param isStaticField whether process only static field or all field
     * @throws DoesNotImplementFluentTQLUserInterfaceException       If class uses FluentTQLSpecificationClass annotation but does not implement FluentTQLUserInterface.
     * @throws ImportAndProcessAnnotationException                   If fails to import and process the field's annotation.
     * @throws FieldNullPointerException                             If the annotated field is not initialized while declaring it.
     * @throws IncompleteMethodDeclarationException                  If the Method field is not configured with the taint flow information using the annotations.
     * @throws FieldNotPublicException                               If the annotated field is not in public modifier.
     * @throws NotAFluentTQLRelatedClassException                    If class is not annotated with one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation.
     * @throws MissingFluentTQLSpecificationClassAnnotationException If class implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation.
     * @throws NotFoundZeroArgumentConstructorException              Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     */
    private static void processSingleField(Field field, Object fluentTQLSpec, boolean isStaticField) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, NotFoundZeroArgumentConstructorException {
        try {
            Object obj;

            if (isStaticField)
                obj = field.get(fluentTQLSpec.getClass());
            else
                obj = field.get(fluentTQLSpec);

            if (obj == null) {
                throw new FieldNullPointerException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
            }

            if (field.getType().equals(Method.class) &&
                    !field.isAnnotationPresent(InFlowParam.class) &&
                    !field.isAnnotationPresent(InFlowThisObject.class) &&
                    !field.isAnnotationPresent(OutFlowParam.class) &&
                    !field.isAnnotationPresent(OutFlowReturnValue.class) &&
                    !field.isAnnotationPresent(OutFlowThisObject.class))
                throw new IncompleteMethodDeclarationException(field.getName(), fluentTQLSpec.getClass().getSimpleName());

            InputDeclarationImpl inputDeclaration = new InputDeclarationImpl();
            OutputDeclarationImpl outputDeclaration = new OutputDeclarationImpl();

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().equals(InFlowParam.class)) {
                    int[] paramIDs = ((InFlowParam) annotation).parameterID();

                    for (int paramID : paramIDs) {
                        inputDeclaration.addInput(
                                new ParameterImpl(paramID)
                        );
                    }
                } else if (annotation.annotationType().equals(OutFlowParam.class)) {
                    int[] paramIDs = ((OutFlowParam) annotation).parameterID();

                    for (int paramID : paramIDs) {
                        outputDeclaration.addOutput(
                                new ParameterImpl(paramID)
                        );
                    }
                } else if (annotation.annotationType().equals(InFlowThisObject.class)) {
                    inputDeclaration.addInput(
                            new ThisObjectImpl()
                    );
                } else if (annotation.annotationType().equals(OutFlowReturnValue.class)) {
                    outputDeclaration.addOutput(
                            new ReturnImpl()
                    );
                } else if (annotation.annotationType().equals(OutFlowThisObject.class)) {
                    outputDeclaration.addOutput(
                            new ThisObjectImpl()
                    );
                } else if (annotation.annotationType().equals(ImportAndProcessAnnotation.class)) {
                    importAndProcess(obj, fluentTQLSpec);
                }
            }

            if (obj instanceof MethodSelector) {
                ((MethodSelector) obj).getOutputDeclaration().getOutputs().clear();
                ((MethodSelector) obj).getInputDeclaration().getInputs().clear();

                ((MethodSelector) obj).getOutputDeclaration().getOutputs().addAll(outputDeclaration.getOutputs());
                ((MethodSelector) obj).getInputDeclaration().getInputs().addAll(inputDeclaration.getInputs());
            }
        } catch (IllegalAccessException e) {
            throw new FieldNotPublicException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
        }
    }

    /**
     * This method tries to import and process the annotation of a field annotated with ImportAndProcessAnnotation
     *
     * @param obj           Object
     * @param fluentTQLSpec Object that tries to import and process the above Object
     * @throws ImportAndProcessAnnotationException If fails to import and process the field's annotation. It wraps all the exception in this method and adds the reason in this exception
     */
    private static void importAndProcess(Object obj, Object fluentTQLSpec) throws ImportAndProcessAnnotationException {
        if ((!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) &&
                (!obj.getClass().isAnnotationPresent(FluentTQLRepositoryClass.class))) {
            throw new ImportAndProcessAnnotationException(obj.getClass().getSimpleName(),
                    fluentTQLSpec.getClass().getSimpleName(), obj.getClass() + " is not a FluentTQL related class. Use one of the " +
                    "[FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation to make it FluentTQL related class.");
        }

        try {
            processFluentTQLAnnotation(obj);
        } catch (ImportAndProcessAnnotationException | FieldNullPointerException | IncompleteMethodDeclarationException | FieldNotPublicException | MissingFluentTQLSpecificationClassAnnotationException | NotAFluentTQLRelatedClassException | DoesNotImplementFluentTQLUserInterfaceException | NotFoundZeroArgumentConstructorException e) {
            throw new ImportAndProcessAnnotationException(
                    obj.getClass().getSimpleName(),
                    fluentTQLSpec.getClass().getSimpleName(),
                    e.getMessage()
            );
        }
    }

    /**
     * This method tries to process the ImportAndProcessOnlyStaticFields annotation of a class.
     *
     * @param fluentTQLRelatedClass Object with ImportAndProcessOnlyStaticFields annotation
     * @throws NotFoundZeroArgumentConstructorException Field annotated with ImportAndProcess related annotation, and that type does not contain a constructor with 0 arguments.
     * @throws ImportAndProcessAnnotationException      If fails to import and process the field's annotation. It wraps all the exception in this method and adds the reason in this exception
     */
    private static void importAndProcessStaticOnly(Object fluentTQLRelatedClass) throws NotFoundZeroArgumentConstructorException, ImportAndProcessAnnotationException {
        ImportAndProcessOnlyStaticFields importAndProcessOnlyStaticFields = fluentTQLRelatedClass.getClass().getAnnotation(ImportAndProcessOnlyStaticFields.class);
        for (Class<?> className : importAndProcessOnlyStaticFields.classList()) {
            Constructor<?> constructor = null;

            boolean hasValidConstructor = false;
            for (Constructor<?> cons : className.getConstructors()) {
                if (cons.getParameterCount() == 0) {
                    hasValidConstructor = true;
                    constructor = cons;
                    break;
                }
            }

            if (!hasValidConstructor)
                throw new NotFoundZeroArgumentConstructorException(className.getSimpleName());

            Object ob = null;
            try {
                ob = constructor.newInstance((Object[]) null);
                //Todo: decide what to do with these exceptions
            } catch (Exception | Error ex) {
                throw new ImportAndProcessAnnotationException(
                        className.getSimpleName(),
                        fluentTQLRelatedClass.getClass().getSimpleName(),
                        ex.getMessage()
                );
            }

            try {
                isValidFluentTQLRelatedClass(ob);

                processEachField(ob, true);
            } catch (ImportAndProcessAnnotationException | NotAFluentTQLRelatedClassException | FieldNullPointerException | MissingFluentTQLSpecificationClassAnnotationException | DoesNotImplementFluentTQLUserInterfaceException | FieldNotPublicException | IncompleteMethodDeclarationException e) {
                throw new ImportAndProcessAnnotationException(
                        className.getSimpleName(),
                        fluentTQLRelatedClass.getClass().getSimpleName(),
                        e.getMessage()
                );
            }
        }
    }
}
