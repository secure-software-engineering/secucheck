package secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessAnnotation;
import secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessOnlyStaticFields;
import secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {SecondRepository.class, SanitizerRepo2.class})
public class ComplexImportCaseSpec implements FluentTQLUserInterface {
    @ImportAndProcessAnnotation
    public SimpleRepository simpleRepository = new SimpleRepository();


    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery noSQLInjection = new TaintFlowQueryBuilder("id13")
                .from(SecondRepository.methodSet)
                .notThrough(SanitizerRepo2.sanitizers)
                .to(simpleRepository.sinks)
                .report("This is complex import spec example")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(noSQLInjection);

        return myFluentTQLSpecs;
    }
}
