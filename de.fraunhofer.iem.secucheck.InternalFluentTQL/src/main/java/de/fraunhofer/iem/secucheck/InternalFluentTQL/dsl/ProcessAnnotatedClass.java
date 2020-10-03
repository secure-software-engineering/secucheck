package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessAnnotatedClass {
    public static List<FluentTQLSpecification> processAnnotationAndGetSpecifications(Object fluentTQLSpec) {
        Object obj = ProcessAnnotatedClass.processAnnotatedTaintFLow(
                fluentTQLSpec
        );

        List<FluentTQLSpecification> fluentTQLSpecifications = new ArrayList<>();

        if (obj instanceof FluentTQLUserInterface) {
            fluentTQLSpecifications.addAll(
                    ((FluentTQLUserInterface) obj).getFluentTQLSpecification()
            );
        }

        return fluentTQLSpecifications;
    }

    public static Object processAnnotatedTaintFLow(Object fluentTQLSpec) {
        if (fluentTQLSpec.getClass().isAnnotationPresent(FluentTQLSpecificationClass.class)) {
            if (!(fluentTQLSpec instanceof FluentTQLUserInterface)) {
                //Todo: Throws an custom exception
                System.out.println("Given Object does not implement FluentTQLUserInterface");
            } else {
                processEachField(fluentTQLSpec);
        //        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) fluentTQLSpec;

                return fluentTQLSpec;
            }
        } else {
            //Todo: Check it annotates ExportForFluentTQLSpec
            System.out.println("Coming soon");
        }

        return null;
    }

    private static void processEachField(Object fluentTQLSpec) {
        System.out.println("\n\n*******" + fluentTQLSpec.getClass().getName() + "*****");
        for (Field field : fluentTQLSpec.getClass().getDeclaredFields()) {
            if (field.getType().equals(Method.class))
                System.out.println("\t*******" + field.getName() + "*****");
                processSingleField(field, fluentTQLSpec);
        }
    }

    private static void processSingleField(Field field, Object fluentTQLSpec) {
        try {
            Object obj = field.get(fluentTQLSpec);

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
                    System.out.println("\tInParam = " + Arrays.toString(((InFlowParam) annotation).parameterID()));
                } else if (annotation.annotationType().equals(OutFlowParam.class)) {
                    int[] paramIDs = ((OutFlowParam) annotation).parameterID();

                    for (int paramID : paramIDs) {
                        outputDeclaration.addOutput(
                                new ParameterImpl(paramID)
                        );
                    }
                    System.out.println("\tOutParam = " + Arrays.toString(((OutFlowParam) annotation).parameterID()));
                } else if (annotation.annotationType().equals(InFlowThisObject.class)) {
                    inputDeclaration.addInput(
                            new ThisObjectImpl()
                    );
                    System.out.println("\tInThisObject");
                } else if (annotation.annotationType().equals(OutFlowReturnValue.class)) {
                    outputDeclaration.addOutput(
                            new ReturnImpl()
                    );
                    System.out.println("\tOutReturnValue");
                } else if (annotation.annotationType().equals(OutFlowThisObject.class)) {
                    outputDeclaration.addOutput(
                            new ThisObjectImpl()
                    );
                    System.out.println("\tOutThisObject");
                } else if (annotation.annotationType().equals(ImportAndProcessAnnotation.class)) {
                    processEachField(obj);
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
