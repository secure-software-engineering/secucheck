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

public class SqlInjectionLesson5bSpec implements FluentTQLUserInterface {
    Method source = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5b: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String,java.lang.String,javax.servlet.http.HttpServletRequest)")
            .out().param(0)
            .configure();

    Method propagator = new MethodConfigurator(
            "java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String,int,int)")
            .in().param(0)
            .out().returnValue()
            .configure();

    Method sink = new MethodConfigurator(
            "java.sql.PreparedStatement: java.sql.ResultSet executeQuery()")
            .in().thisObject()
            .configure();

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder("SQLInjectionLesson5b")
                .from(source)
                .through(propagator)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson5b")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow);

        return myFluentTQLSpecs;
    }
}
