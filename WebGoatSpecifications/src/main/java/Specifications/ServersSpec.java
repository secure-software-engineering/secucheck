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

public class ServersSpec implements FluentTQLUserInterface {
    Method source = new MethodConfigurator(
            "org.owasp.webgoat.sql_injection.mitigation.Servers: " +
                    "java.util.List " +
                    "sort(java.lang.String)")
            .out().param(0)
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
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder("Servers_SQLiWithPreparedStmt")
                .from(source)
                .through(propagator)
                .to(sink)
                .report("Webgoat application: Mitigation -> Server")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow);

        return myFluentTQLSpecs;
    }
}
