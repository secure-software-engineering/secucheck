package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.DoesNotImplementFluentTQLUserInterfaceException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FieldNullPointerException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.ImportAndProcessAnnotationException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.NotAFluentTQLSpecificationException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessAnnotatedClass {
    public static List<FluentTQLSpecification> processAnnotationAndGetSpecifications(Object fluentTQLSpec) throws NotAFluentTQLSpecificationException, DoesNotImplementFluentTQLUserInterfaceException, ImportAndProcessAnnotationException, FieldNullPointerException {

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

    public static Object processFluentTQLAnnotation(Object fluentTQLSpec) throws ImportAndProcessAnnotationException, FieldNullPointerException {
        processEachField(fluentTQLSpec);
        return fluentTQLSpec;
    }

    private static void processEachField(Object fluentTQLSpec) throws ImportAndProcessAnnotationException, FieldNullPointerException {
        for (Field field : fluentTQLSpec.getClass().getDeclaredFields()) {
            processSingleField(field, fluentTQLSpec);
        }
    }

    private static void processSingleField(Field field, Object fluentTQLSpec) throws ImportAndProcessAnnotationException, FieldNullPointerException {
        try {
            Object obj = field.get(fluentTQLSpec);

            if (obj == null) {
                throw new FieldNullPointerException(field.getName(), fluentTQLSpec.getClass().getSimpleName());
            }

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
                ((MethodSelector) obj).setOutputDeclaration(outputDeclaration);
                ((MethodSelector) obj).setInputDeclaration(inputDeclaration);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
