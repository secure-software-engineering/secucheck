package Specifications.FuentTQLRepositories.GeneralPropagators;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class StringGeneralPropagators {
    @GeneralPropagator
    public static Method S_VALUE_OF = new MethodConfigurator("java.lang.String: java.lang.String valueOf(java.lang.Object)")
            .in().param(0)
            .out().returnValue()
            .configure();

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method stringConcat = new MethodSelector(
            "java.lang.String: java.lang.String concat(java.lang.String)");


    // Below all the GPs are not confirmed, get confirmation for below GPs
    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method stringChars = new MethodSelector(
            "java.lang.String: java.util.stream.IntStream chars()");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method stringCodePoints = new MethodSelector(
            "java.lang.String: java.util.stream.IntStream codePoints()");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method stringToCharArray = new MethodSelector(
            "java.lang.String: char[] toCharArray()");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method getBytes1 = new MethodSelector(
            "java.lang.String: byte[] getBytes()");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method getBytes2 = new MethodSelector(
            "java.lang.String: byte[] getBytes(java.lang.String)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method getBytes3 = new MethodSelector(
            "java.lang.String: byte[] getBytes(java.nio.charset.Charset)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method stringSubString1 = new MethodSelector(
            "java.lang.String: java.lang.String substring(int)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method stringSubString2 = new MethodSelector(
            "java.lang.String: java.lang.String substring(int, int)");

    @GeneralPropagator
    @InFlowThisObject
    @OutFlowReturnValue
    public static Method stringSubSequence1 = new MethodSelector(
            "java.lang.String: java.lang.CharSequence subSequence(int, int)");

}
