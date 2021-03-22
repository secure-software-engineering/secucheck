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

public class SqlInjectionLesson8Spec implements FluentTQLUserInterface {
    Method source1 = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson8: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String)")
            .in().param(0)
            .configure();

    Method source2 = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson8: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String)")
            .in().param(1)
            .configure();

    Method sink = new MethodConfigurator(
            "java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)")
            .in().param(0)
            .configure();

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow1 = new TaintFlowQueryBuilder()
                .from(source1)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson8 TF1")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlow2 = new TaintFlowQueryBuilder()
                .from(source2)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson8 TF2")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow1);
        myFluentTQLSpecs.add(taintFlow2);

        return myFluentTQLSpecs;
    }
}
