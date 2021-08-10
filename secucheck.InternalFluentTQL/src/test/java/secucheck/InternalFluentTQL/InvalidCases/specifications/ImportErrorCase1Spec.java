package secucheck.InternalFluentTQL.InvalidCases.specifications;

import secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessOnlyStaticFields;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {InvalidFluentTQLRepository1.class})  //Fixme: This is the problem/error
public class ImportErrorCase1Spec implements FluentTQLUserInterface {
    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("id3")
                .from(InvalidFluentTQLRepository1.source)
                .to(InvalidFluentTQLRepository1.sink)
                .report("There is a dummy Information leak!!!")
                .at(LOCATION.SOURCE)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
