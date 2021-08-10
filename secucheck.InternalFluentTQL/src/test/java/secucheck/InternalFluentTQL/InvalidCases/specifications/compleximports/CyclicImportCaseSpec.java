package secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports;

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
@ImportAndProcessOnlyStaticFields(classList = {FirstRepository.class})
public class CyclicImportCaseSpec implements FluentTQLUserInterface {
    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery noSQLInjection = new TaintFlowQueryBuilder("id1")
                .from(FirstRepository.source1)
                .to(FirstRepository.sink1)
                .report("This is a cyclic import example")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(noSQLInjection);

        return myFluentTQLSpecs;
    }
}
