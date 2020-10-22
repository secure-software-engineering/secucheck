package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class GeneralPropagatorsRepository {
    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp1 = new MethodSelector("java.lang.String: java.lang.String concat(java.lang.String)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp2 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)");

    @GeneralPropagator
    @InFlowThisObject
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method gp3 = new MethodSelector("java.lang.StringBuilder: java.lang.StringBuilder append(char[])");
}
