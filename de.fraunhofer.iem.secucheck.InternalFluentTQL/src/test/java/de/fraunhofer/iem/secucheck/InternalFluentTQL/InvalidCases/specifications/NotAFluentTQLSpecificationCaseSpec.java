package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.MethodDeclarationForNoSQLInjection;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

import java.util.ArrayList;
import java.util.List;

@ImportAndProcessOnlyStaticFields(classList = {MethodDeclarationForNoSQLInjection.class})
@FluentTQLRepositoryClass             // Error: Since it does not uses FluentTQLSpecificationClass annotation and if we try processFluentTQLSpecificationClassAnnotation() method
public class NotAFluentTQLSpecificationCaseSpec {
    public List<FluentTQLSpecification> getFluentTQLSpecification() {

        TaintFlowQuery noSQLInjection = new TaintFlowQueryBuilder()
                .from(MethodDeclarationForNoSQLInjection.source1).notThrough(MethodDeclarationForNoSQLInjection.sanitizer).through(MethodDeclarationForNoSQLInjection.requiredPropagator1).to(MethodDeclarationForNoSQLInjection.sink)
                .and()
                .from(MethodDeclarationForNoSQLInjection.source2).notThrough(MethodDeclarationForNoSQLInjection.sanitizer).through(MethodDeclarationForNoSQLInjection.requiredPropagator1).to(MethodDeclarationForNoSQLInjection.sink)
                .and()
                .from(MethodDeclarationForNoSQLInjection.source3).notThrough(MethodDeclarationForNoSQLInjection.sanitizer).through(MethodDeclarationForNoSQLInjection.requiredPropagator2).to(MethodDeclarationForNoSQLInjection.sink)
                .report("There is a No-SQL-Injection (CWE943) with multiple sources")
                .at(LOCATION.SOURCE)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(noSQLInjection);

        return myFluentTQLSpecs;
    }
}
