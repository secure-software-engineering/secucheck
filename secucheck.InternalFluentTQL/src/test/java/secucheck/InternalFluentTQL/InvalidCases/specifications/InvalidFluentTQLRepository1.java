package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.MethodSelector;
import secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class InvalidFluentTQLRepository1 {  //Fixme: This is the problem/error. Not declared as FluentTQL related class
    @OutFlowReturnValue
    public static Method source = new MethodSelector("Test: String getSecret()");

    @InFlowParam(parameterID = {0})
    public static Method sink = new MethodSelector("Test: void showSecret(String)");
}
