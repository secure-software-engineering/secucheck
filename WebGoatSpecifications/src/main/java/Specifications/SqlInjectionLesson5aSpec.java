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

public class SqlInjectionLesson5aSpec implements FluentTQLUserInterface {
    Method source1 = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5a: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String,java.lang.String,java.lang.String)")
            .out().param(0)
            .configure();

    Method source2 = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5a: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String,java.lang.String,java.lang.String)")
            .out().param(1)
            .configure();

    Method source3 = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5a: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String,java.lang.String,java.lang.String)")
            .out().param(2)
            .configure();

    Method sink = new MethodConfigurator(
            "java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)")
            .in().param(0)
            .configure();

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow1 = new TaintFlowQueryBuilder("SQLInjectionLesson5a_TF1")
                .from(source1)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson5a TF1")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlow2 = new TaintFlowQueryBuilder("SQLInjectionLesson5a_TF2")
                .from(source2)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson5a TF2")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlow3 = new TaintFlowQueryBuilder("SQLInjectionLesson5a_TF3")
                .from(source3)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson5a TF3")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow1);
        myFluentTQLSpecs.add(taintFlow2);
        myFluentTQLSpecs.add(taintFlow3);

        return myFluentTQLSpecs;
    }
}
