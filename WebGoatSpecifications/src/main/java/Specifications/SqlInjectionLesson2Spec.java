package Specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

public class SqlInjectionLesson2Spec implements FluentTQLUserInterface {
    Method source = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson2: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String)")
            .out().param(0)
            .configure();

    Method sanitizer = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson2: " +
                    "java.lang.String " +
                    "sanitize(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    Method sink = new MethodConfigurator(
            "java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)"
    )
            .in().param(0)
            .configure();

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder("SQLInjectionLesson2")
                .from(source)
                .notThrough(sanitizer)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson2")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow);

        return myFluentTQLSpecs;
    }
}
