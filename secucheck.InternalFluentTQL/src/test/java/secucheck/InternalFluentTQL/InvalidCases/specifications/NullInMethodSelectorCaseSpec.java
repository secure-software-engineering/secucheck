package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.MethodSelector;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class NullInMethodSelectorCaseSpec {
    @OutFlowReturnValue
    Method source1 = new MethodSelector(null);   //Fixme: This is the problem/error

    @OutFlowReturnValue
    Method source2 = new MethodSelector("de.ra.Test1: String getSecret()");

    @OutFlowReturnValue
    Method source3 = new MethodSelector("de.ra.Test1: String getContact()");

    @OutFlowReturnValue
    Method source4 = new MethodSelector("de.ra.Test1: String getSSN()");
}
