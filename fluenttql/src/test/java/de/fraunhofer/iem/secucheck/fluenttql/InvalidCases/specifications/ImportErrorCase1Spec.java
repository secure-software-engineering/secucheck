package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.SpecificationInterface.FluentTQLUserInterface;

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