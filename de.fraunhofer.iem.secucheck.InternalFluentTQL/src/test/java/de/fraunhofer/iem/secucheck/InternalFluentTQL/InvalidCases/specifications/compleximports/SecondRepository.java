package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
@ImportAndProcessOnlyStaticFields(classList = {ThirdRepository.class})
public class SecondRepository {
    @OutFlowReturnValue
    public Method source11 = new MethodSelector("Test1: java.lang.String source11()");

    @OutFlowReturnValue
    public Method source22 = new MethodSelector("Test1: java.lang.String source22()");

    @OutFlowReturnValue
    public Method source33 = new MethodSelector("Test1: java.lang.String source33()");

    @OutFlowReturnValue
    public Method source44 = new MethodSelector("Test1: java.lang.String source44()");

    @OutFlowReturnValue
    public Method source55 = new MethodSelector("Test1: java.lang.String source55()");

    @OutFlowReturnValue
    public Method source66 = new MethodSelector("Test1: java.lang.String source66()");
}
