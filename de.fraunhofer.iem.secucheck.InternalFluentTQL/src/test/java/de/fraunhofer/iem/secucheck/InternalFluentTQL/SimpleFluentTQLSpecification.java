package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class SimpleFluentTQLSpecification implements FluentTQLUserInterface {
    @OutFlowReturnValue
    public Method source = new MethodSelector("Test: java.lang.String getSecret()");

    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method sanitizer = new MethodSelector("Test: java.lang.String sanitize(java.lang.String)");

    @InFlowParam(parameterID = {0})
    public static Method sink = new MethodSelector("Test: void printSecret(java.lang.String)");

    public static MethodSet myMethodSet = new MethodSet("Testing MethodSet")
            .addMethod(sanitizer)
            .addMethod(sink);

    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {

        TaintFlowQuery simpleTaintFlow = new TaintFlowQueryBuilder()
                .from(source)
                .notThrough(sanitizer)
                .to(sink)
                .report("A simple TaintFlow is present here!!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecification = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecification.add(simpleTaintFlow);

        return myFluentTQLSpecification;
    }
}
