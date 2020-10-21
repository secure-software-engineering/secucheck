package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessAnnotation;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {SecondRepository.class, SanitizerRepo2.class})
public class ComplexImportCaseSpec implements FluentTQLUserInterface {
    @ImportAndProcessAnnotation
    public SimpleRepository simpleRepository = new SimpleRepository();


    @Override
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery noSQLInjection = new TaintFlowQueryBuilder()
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
