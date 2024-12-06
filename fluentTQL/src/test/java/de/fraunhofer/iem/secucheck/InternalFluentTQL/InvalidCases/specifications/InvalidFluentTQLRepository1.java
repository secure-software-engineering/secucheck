package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class InvalidFluentTQLRepository1 {  //Fixme: This is the problem/error. Not declared as FluentTQL related class
    @OutFlowReturnValue
    public static Method source = new MethodSelector("Test: String getSecret()");

    @InFlowParam(parameterID = {0})
    public static Method sink = new MethodSelector("Test: void showSecret(String)");
}
