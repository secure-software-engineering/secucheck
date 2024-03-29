package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowThisObject;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class SanitizerRepo1 {
    @InFlowThisObject
    @OutFlowReturnValue
    public Method sanitizer1 = new MethodSelector("Test1: java.lang.String sanitizer1()");

    @InFlowThisObject
    @OutFlowReturnValue
    public Method sanitizer2 = new MethodSelector("Test1: java.lang.String sanitizer2()");

    @InFlowThisObject
    @OutFlowReturnValue
    public Method sanitizer3 = new MethodSelector("Test1: java.lang.String sanitizer3()");

    @InFlowThisObject
    @OutFlowReturnValue
    public Method sanitizer4 = new MethodSelector("Test1: java.lang.String sanitizer4()");

    @InFlowThisObject
    @OutFlowReturnValue
    public Method sanitizer5 = new MethodSelector("Test1: java.lang.String sanitizer5()");

    @InFlowThisObject
    @OutFlowReturnValue
    public Method sanitizer6 = new MethodSelector("Test1: java.lang.String sanitizer6()");
}
