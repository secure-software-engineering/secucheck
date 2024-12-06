package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessAnnotation;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class FirstRepository {
    @ImportAndProcessAnnotation
    public static SecondRepository secondRepository = new SecondRepository();

    @OutFlowReturnValue
    public static Method source1 = new MethodSelector("Test1: java.lang.String source1()");

    @OutFlowReturnValue
    public static Method sink1 = new MethodSelector("Test1: java.lang.String sink1()");

    @OutFlowReturnValue
    public static Method source3 = new MethodSelector("Test1: java.lang.String source3()");

    @OutFlowReturnValue
    public static Method source4 = new MethodSelector("Test1: java.lang.String source4()");

    @OutFlowReturnValue
    public static Method source5 = new MethodSelector("Test1: java.lang.String source5()");

    @OutFlowReturnValue
    public static Method source6 = new MethodSelector("Test1: java.lang.String source6()");
}
