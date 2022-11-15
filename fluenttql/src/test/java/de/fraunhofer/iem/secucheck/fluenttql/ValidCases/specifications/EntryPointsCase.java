package de.fraunhofer.iem.secucheck.fluenttql.ValidCases.specifications;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.AnalysisEntryPoint;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class EntryPointsCase {
    @AnalysisEntryPoint
    public Method source1 = new MethodSelector("Test1: java.lang.String getSecret1()");

    @AnalysisEntryPoint
    public Method source2 = new MethodSelector("Test1: java.lang.String getSecret2()");

    @AnalysisEntryPoint
    @OutFlowReturnValue
    public Method source3 = new MethodSelector("Test1: java.lang.String getSecret3()");

    @AnalysisEntryPoint
    @OutFlowReturnValue
    public Method source4 = new MethodSelector("Test1: java.lang.String getSecret4()");

    @OutFlowReturnValue
    public Method source5 = new MethodSelector("Test1: java.lang.String getSecret5()");

    @OutFlowReturnValue
    public Method source6 = new MethodSelector("Test1: java.lang.String getSecret6()");

    @AnalysisEntryPoint
    public static Method source7 = new MethodSelector("Test1: java.lang.String getSecret7()");

    @AnalysisEntryPoint
    @OutFlowReturnValue
    public static Method source8 = new MethodSelector("Test1: java.lang.String getSecret8()");

    @OutFlowReturnValue
    public static Method source9 = new MethodSelector("Test1: java.lang.String getSecret9()");

    @OutFlowReturnValue
    public static Method source10 = new MethodSelector("Test1: java.lang.String getSecret10()");

    @OutFlowReturnValue
    public static Method source11 = new MethodSelector("Test1: java.lang.String getSecret11()");

    @AnalysisEntryPoint
    public MethodSet methodSet = new MethodSet("DummyTest")
            .addMethod(source1)
            .addMethod(source2)
            .addMethod(source3)
            .addMethod(source4)
            .addMethod(source10)
            .addMethod(source11);
}
