package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import secucheck.InternalFluentTQL.dsl.MethodSelector;
import secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class NullInNotThroughMethodCaseSpec implements FluentTQLUserInterface {
    @OutFlowReturnValue
    public Method source = new MethodSelector("Test: String getSecret()");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector("Test: void showSecret(String)");


    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("id9")
                .from(source)
                .notThrough(null)        //Fixme: This is the problem/error
                .to(sink)
                .report("There is a dummy Information leak!!!")
                .at(LOCATION.SINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
