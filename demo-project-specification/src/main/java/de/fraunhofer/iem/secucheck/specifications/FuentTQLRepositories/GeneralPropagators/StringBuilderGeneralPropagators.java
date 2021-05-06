package de.fraunhofer.iem.secucheck.specifications.FuentTQLRepositories.GeneralPropagators;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class StringBuilderGeneralPropagators {
    @GeneralPropagator
    public static Method SB_TO_STRING = new MethodConfigurator("java.lang.StringBuilder: java.lang.String toString()")
            .in().thisObject()
            .out().returnValue()
            .configure();

    @GeneralPropagator
    public static Method SB_INIT = new MethodConfigurator("java.lang.StringBuilder: void <init>(java.lang.String)")
            .in().param(0)
            .out().thisObject()
            .configure();

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp1 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp2 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.Object)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp3 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.StringBuffer)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp4 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.CharSequence)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp5 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.CharSequence, int, int)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp6 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(char[])");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp7 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(char[], int, int)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method gp8 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(boolean)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp9 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(char)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp10 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(int)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method gp11 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(long)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method gp12 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(float)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method gp13 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(double)");
}
