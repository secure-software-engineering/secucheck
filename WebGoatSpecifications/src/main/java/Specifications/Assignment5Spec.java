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

public class Assignment5Spec implements FluentTQLUserInterface {
    Method source = new MethodConfigurator(
            "org.owasp.webgoat.challenges.challenge5.Assignment5: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "login(java.lang.String,java.lang.String)")
            .out().param(1)
            .configure();

    Method propagator = new MethodConfigurator(
            "java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    Method sink = new MethodConfigurator(
            "java.sql.PreparedStatement: java.sql.ResultSet executeQuery()")
            .in().thisObject()
            .configure();

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder("Assignment5_SQLInjection")
                .from(source)
                .through(propagator)
                .to(sink)
                .report("Webgoat application: Challenge5 -> Assignment5")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow);

        return myFluentTQLSpecs;
    }
}
