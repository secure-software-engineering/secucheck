package Specifications.FuentTQLRepositories.GeneralPropagators;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.GeneralPropagator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class Java11ConcatGeneralPropagators {
    @GeneralPropagator
    public static Method JAVA_11_STR_CONCAT1 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method JAVA_11_STR_CONCAT2 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String)")
            .in().param(0).param(1)
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method JAVA_11_STR_CONCAT3 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String,java.lang.String)")
            .in().param(0).param(1).param(2)
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method JAVA_11_STR_CONCAT4 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String,java.lang.String,java.lang.String)")
            .in().param(0).param(1).param(2).param(3)
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method JAVA_11_STR_CONCAT5 = new MethodConfigurator("java.lang.String makeConcatWithConstants(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)")
            .in().param(0).param(1).param(2).param(3).param(4)
            .out().returnValue()
            .configure();
}
