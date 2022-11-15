package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
@ImportAndProcessOnlyStaticFields(classList = {FirstRepository.class})
public class ThirdRepository {
    @OutFlowReturnValue
    public static Method source111 = new MethodSelector("Test1: java.lang.String source111()");

    @OutFlowReturnValue
    public static Method source222 = new MethodSelector("Test1: java.lang.String source222()");

    @OutFlowReturnValue
    public static Method source333 = new MethodSelector("Test1: java.lang.String source333()");

    @OutFlowReturnValue
    public static Method source444 = new MethodSelector("Test1: java.lang.String source444()");

    @OutFlowReturnValue
    public static Method source555 = new MethodSelector("Test1: java.lang.String source555()");

    @OutFlowReturnValue
    public static Method source666 = new MethodSelector("Test1: java.lang.String source666()");
}
