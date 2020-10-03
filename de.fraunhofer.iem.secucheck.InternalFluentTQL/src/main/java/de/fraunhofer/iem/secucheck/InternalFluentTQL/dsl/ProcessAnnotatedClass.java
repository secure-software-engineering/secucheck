package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ProcessAnnotatedClass {
    public static List<FluentTQLSpecification> processAnnotationAndGetSpecifications(Object fluentTQLSpec) throws NotAFluentTQLSpecificationException, DoesNotImplementFluentTQLUserInterfaceException, ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException {

        if (!fluentTQLSpec.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) {
            throw new NotAFluentTQLSpecificationException(fluentTQLSpec.getClass().getName());
        } else {
            if (!(fluentTQLSpec instanceof FluentTQLUserInterface)) {
                throw new DoesNotImplementFluentTQLUserInterfaceException(fluentTQLSpec.getClass().getName());
            } else {
                Object obj = ProcessAnnotatedClass.processFluentTQLAnnotation(
                        fluentTQLSpec
                );

                List<FluentTQLSpecification> fluentTQLSpecifications = new ArrayList<>();

                fluentTQLSpecifications.addAll(
                        ((FluentTQLUserInterface) obj).getFluentTQLSpecification()
                );

                return fluentTQLSpecifications;
            }
        }
    }

    public static Object processFluentTQLAnnotation(Object fluentTQLRelatedClass) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException {
        processEachField(fluentTQLRelatedClass);
        return fluentTQLRelatedClass;
    }

    private static void processEachField(Object fluentTQLSpec) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException {
        for (Field field : fluentTQLSpec.getClass().getDeclaredFields()) {
            processSingleField(field, fluentTQLSpec);
        }
    }

    private static void processSingleField(Field field, Object fluentTQLSpec) throws ImportAndProcessAnnotationException, FieldNullPointerException, IncompleteMethodDeclarationException {
        try {
            Object obj = field.get(fluentTQLSpec);

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
                ((MethodSelector) obj).getOutputDeclaration().getOutputs().addAll(outputDeclaration.getOutputs());
                ((MethodSelector) obj).getInputDeclaration().getInputs().addAll(inputDeclaration.getInputs());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
