package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import secucheck.InternalFluentTQL.dsl.MethodSelector;
import secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import secucheck.InternalFluentTQL.dsl.annotations.AnalysisEntryPoint;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class EntrypointMethodInTaintFlowCaseSpec implements FluentTQLUserInterface {
    @AnalysisEntryPoint
    public Method entryPointMethod = new MethodSelector("Test: String append(String)");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector("Test: void display(String)");

    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("id2")
                .from(entryPointMethod)
                .to(sink)
                .report("There is a dummy Information leak!!!")
                .at(LOCATION.SOURCE)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}