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
public class SqlInjectionLesson4Spec implements FluentTQLUserInterface {
    @InFlowParam(parameterID = {0})
    public Method source = new MethodSelector(
            "org.owasp.webgoat.sql_injection.introduction.SqlInjectionLesson4: " +
                    "org.owasp.webgoat.assignments.AttackResult " +
                    "completed(java.lang.String)");

    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector(
            "java.sql.Statement: int executeUpdate(java.lang.String)"
    );

    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery taintFlow = new TaintFlowQueryBuilder()
                .from(source)
                .to(sink)
                .report("Webgoat application: Introduction -> SqlInjectionLesson4")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(taintFlow);

        return myFluentTQLSpecs;
    }
}
