package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class ProcessAnnotatedClass {
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

    public static FluentTQLUserInterface processFluentTQLSpecificationClassAnnotation(Object fluentTQLSpec) throws DoesNotImplementFluentTQLUserInterfaceException, ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, NotAFluentTQLRelatedClassException, MissingFluentTQLSpecificationClassAnnotationException, NotFoundZeroArgumentConstructorException {
        isValidFluentTQLRelatedClass(fluentTQLSpec);
        return (FluentTQLUserInterface) ProcessAnnotatedClass.processFluentTQLAnnotation(fluentTQLSpec);
    }

    public static Object processFluentTQLAnnotation(Object fluentTQLRelatedClass) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException, FieldNotPublicException, MissingFluentTQLSpecificationClassAnnotationException, DoesNotImplementFluentTQLUserInterfaceException, NotAFluentTQLRelatedClassException, NotFoundZeroArgumentConstructorException {
        isValidFluentTQLRelatedClass(fluentTQLRelatedClass);

        if (fluentTQLRelatedClass.getClass().isAnnotationPresent(ImportAndProcessOnlyStaticFields.class)) {
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
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                isValidFluentTQLRelatedClass(ob);

                processEachField(ob, true);

            }
        }

        processEachField(fluentTQLRelatedClass, false);
        return fluentTQLRelatedClass;
    }

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
                    if ((!obj.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) &&
                            (!obj.getClass().isAnnotationPresent(FluentTQLRepositoryClass.class))) {
                        throw new ImportAndProcessAnnotationException(obj.getClass().getName());
                    }

                    processFluentTQLAnnotation(obj);
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
}
