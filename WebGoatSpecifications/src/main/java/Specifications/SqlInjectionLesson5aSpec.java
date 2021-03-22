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
public class SqlInjectionLesson5aSpec implements FluentTQLUserInterface {
    @InFlowParam(parameterID = {0})
    public Method source1 = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5a: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String, java.lang.String)");

    @InFlowParam(parameterID = {1})
    public Method source2 = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5a: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String, java.lang.String)");

    @InFlowParam(parameterID = {2})
    public Method source3 = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson5a: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String, java.lang.String)");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector(
            "java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)");

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow1 = new TaintFlowQueryBuilder()
                .from(source1)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson5a TF1")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlow2 = new TaintFlowQueryBuilder()
                .from(source2)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson5a TF2")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery taintFlow3 = new TaintFlowQueryBuilder()
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
