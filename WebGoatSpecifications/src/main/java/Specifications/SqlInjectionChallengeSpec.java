package Specifications;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

@FluentTQLSpecificationClass
public class SqlInjectionChallengeSpec implements FluentTQLUserInterface {
    @InFlowParam(parameterID = {0})
    public Method source = new MethodSelector(
            "org.owasp.webgoat.sql_injection.advanced.SqlInjectionChallenge: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "registerNewUser(java.lang.String, java.lang.String, java.lang.String)");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector(
            "java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)");

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow1 = new TaintFlowQueryBuilder()
                .from(source)
                .to(sink)
                .report("Webgoat application: advanced -> SqlInjectionChallenge")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow1);

        return myFluentTQLSpecs;
    }
}
