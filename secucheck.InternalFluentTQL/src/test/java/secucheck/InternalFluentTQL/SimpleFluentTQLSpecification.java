package secucheck.InternalFluentTQL;

import secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import secucheck.InternalFluentTQL.dsl.MethodSet;
import secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

public class SimpleFluentTQLSpecification implements FluentTQLUserInterface {
    static Method source = new MethodConfigurator("Test: java.lang.String getSecret()")
            .out().returnValue()
            .configure();

    static Method sanitizer = new MethodConfigurator("Test: java.lang.String sanitize(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    static Method sink = new MethodConfigurator("Test: void printSecret(java.lang.String)")
            .in().param(0)
            .configure();

    static MethodSet myMethodSet = new MethodSet("Testing MethodSet")
            .addMethod(source)
            .addMethod(sanitizer)
            .addMethod(sink);

    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {

        TaintFlowQuery simpleTaintFlow = new TaintFlowQueryBuilder("SimpleSpec")
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
