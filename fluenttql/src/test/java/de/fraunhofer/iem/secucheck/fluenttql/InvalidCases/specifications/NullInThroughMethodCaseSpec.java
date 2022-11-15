package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class NullInThroughMethodCaseSpec implements FluentTQLUserInterface {
    @OutFlowReturnValue
    public Method source = new MethodSelector("Test: String getSecret()");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector("Test: void showSecret(String)");


    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("id11")
                .from(source)
                .through(null)        //Fixme: This is the problem/error
                .to(sink)
                .report("There is a dummy Information leak!!!")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
