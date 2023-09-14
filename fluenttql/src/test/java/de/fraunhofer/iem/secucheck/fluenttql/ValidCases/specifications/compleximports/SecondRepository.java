package de.fraunhofer.iem.secucheck.fluenttql.ValidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method;

@FluentTQLRepositoryClass
@ImportAndProcessOnlyStaticFields(classList = {FirstRepository.class})
public class SecondRepository {
    @OutFlowReturnValue
    public static Method source7 = new MethodSelector("Test1: java.lang.String source7()");

    @OutFlowReturnValue
    public static Method source8 = new MethodSelector("Test1: java.lang.String source8()");

    @OutFlowReturnValue
    public static Method source9 = new MethodSelector("Test1: java.lang.String source9()");

    @OutFlowReturnValue
    public static Method source10 = new MethodSelector("Test1: java.lang.String source10()");

    @OutFlowReturnValue
    public static Method source11 = new MethodSelector("Test1: java.lang.String source11()");

    @OutFlowReturnValue
    public static Method source12 = new MethodSelector("Test1: java.lang.String source12()");

    public static MethodSet methodSet = new MethodSet("Dummy1")
            .addMethod(FirstRepository.source1)
            .addMethod(FirstRepository.source2)
            .addMethod(FirstRepository.source3)
            .addMethod(FirstRepository.source4)
            .addMethod(FirstRepository.source5)
            .addMethod(FirstRepository.source6)
            .addMethod(source7)
            .addMethod(source8)
            .addMethod(source9)
            .addMethod(source10)
            .addMethod(source11)
            .addMethod(source12);
}
