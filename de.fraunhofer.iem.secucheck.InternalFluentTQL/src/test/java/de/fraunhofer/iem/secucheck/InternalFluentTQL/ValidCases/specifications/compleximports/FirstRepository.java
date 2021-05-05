package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class FirstRepository {
    @OutFlowReturnValue
    public static Method source1 = new MethodSelector("Test1: java.lang.String source1()");

    @OutFlowReturnValue
    public static Method source2 = new MethodSelector("Test1: java.lang.String source2()");

    @OutFlowReturnValue
    public static Method source3 = new MethodSelector("Test1: java.lang.String source3()");

    @OutFlowReturnValue
    public static Method source4 = new MethodSelector("Test1: java.lang.String source4()");

    @OutFlowReturnValue
    public static Method source5 = new MethodSelector("Test1: java.lang.String source5()");

    @OutFlowReturnValue
    public static Method source6 = new MethodSelector("Test1: java.lang.String source6()");
}
