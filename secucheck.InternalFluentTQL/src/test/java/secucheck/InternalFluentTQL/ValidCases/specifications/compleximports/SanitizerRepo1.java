package secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import secucheck.InternalFluentTQL.dsl.MethodSelector;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import secucheck.InternalFluentTQL.dsl.annotations.InFlowThisObject;
import secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

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
