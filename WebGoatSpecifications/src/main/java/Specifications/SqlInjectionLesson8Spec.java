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
public class SqlInjectionLesson8Spec implements FluentTQLUserInterface {
    @InFlowParam(parameterID = {0})
    public Method source1 = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson8: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String)");

    @InFlowParam(parameterID = {1})
    public Method source2 = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson8: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String, java.lang.String)");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector(
            "java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)");

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
