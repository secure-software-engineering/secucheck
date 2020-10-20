package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.AnalysisEntryPoint;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class EntryPointsCase {
    @AnalysisEntryPoint
    public Method source1 = new MethodSelector("Test1: java.lang.String getSecret1()");

    @AnalysisEntryPoint
    public Method source2 = new MethodSelector("Test1: java.lang.String getSecret2()");

    @AnalysisEntryPoint
    public Method source3 = new MethodSelector("Test1: java.lang.String getSecret3()");

    @AnalysisEntryPoint
    public Method source4 = new MethodSelector("Test1: java.lang.String getSecret4()");

    public Method source5 = new MethodSelector("Test1: java.lang.String getSecret5()");

    public Method source6 = new MethodSelector("Test1: java.lang.String getSecret6()");

    @AnalysisEntryPoint
    public static Method source7 = new MethodSelector("Test1: java.lang.String getSecret7()");

    @AnalysisEntryPoint
    public static Method source8 = new MethodSelector("Test1: java.lang.String getSecret8()");

    public static Method source9 = new MethodSelector("Test1: java.lang.String getSecret9()");

    public static Method source10 = new MethodSelector("Test1: java.lang.String getSecret10()");

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
