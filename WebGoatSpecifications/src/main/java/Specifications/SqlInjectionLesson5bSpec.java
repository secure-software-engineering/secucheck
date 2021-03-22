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
public class SqlInjectionLesson5bSpec implements FluentTQLUserInterface {
    @InFlowParam(parameterID = {0})
    public Method source = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5b: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest)");

    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method propagator = new MethodSelector(
            "java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String, int, int)");

    @InFlowThisObject
    public Method sink = new MethodSelector(
            "java.sql.PreparedStatement: java.sql.ResultSet executeQuery()");

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder()
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
