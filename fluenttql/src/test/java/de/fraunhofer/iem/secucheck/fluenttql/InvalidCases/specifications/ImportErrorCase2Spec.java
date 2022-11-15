package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {InvalidFluentTQLRepository2.class})  //Fixme: This is the problem/error
public class ImportErrorCase2Spec implements FluentTQLUserInterface {
    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("id4")
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
