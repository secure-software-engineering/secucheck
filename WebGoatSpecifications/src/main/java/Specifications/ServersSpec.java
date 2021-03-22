package Specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowThisObject;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class ServersSpec implements FluentTQLUserInterface {
    @InFlowParam(parameterID = {0})
    public Method source = new MethodSelector(
            "org.owasp.webgoat.sql_injection.mitigation.Server: " +
                    "java.util.List " +
                    "sort(java.lang.String)");

    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method propagator = new MethodSelector(
            "java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String)");

    @InFlowThisObject
    public Method sink = new MethodSelector(
            "java.sql.PreparedStatement: java.sql.ResultSet executeQuery()");

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder()
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
