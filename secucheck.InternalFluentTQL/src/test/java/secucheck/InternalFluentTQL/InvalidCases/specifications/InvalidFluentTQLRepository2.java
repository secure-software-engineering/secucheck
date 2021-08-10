package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.MethodSelector;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

@FluentTQLRepositoryClass
public class InvalidFluentTQLRepository2 {
    public static Method source = new MethodSelector("Test: String getSecret()");  //Fixme: This is the problem/error. Incomplete Method

    @InFlowParam(parameterID = {0})
    public static Method sink = new MethodSelector("Test: void showSecret(String)");
}
