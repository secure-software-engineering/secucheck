package de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FluentTQLSpecifications.TrustBoundaryViolation.CWE501;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.FuentTQLRepositories.Sources.ServletSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.ImportAndProcessOnlyStaticFields;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.InFlowParam;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.OutFlowReturnValue;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal FluentTQL specification for Trust-Boundary violation.
 * Note: This is an example of trust boundary violation. To avoid trust boundary violation, it is better never to send user input to session object.
 *
 * @author Ranjith Krishnamurthy
 */
@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {ServletSources.class})
public class TrustBoundaryViolationSpec implements FluentTQLUserInterface {
    /**
     * This is a sanitizer for this example that authenticate the username and then returns the valid username. If authentication fails then
     * it returns the null so that user input will not be directly set in session object to avoid trust boundary violation.
     */
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method sanitizer = new MethodSelector("catalog.TrustBoundaryViolation.CWE501.TrustBoundaryViolation: java.lang.String authenticate(java.lang.String, java.lang.String)");

    /**
     * sink
     */
    @InFlowParam(parameterID = {1})
    public Method sink = new MethodSelector("javax.servlet.http.HttpSession: void setAttribute(java.lang.String, java.lang.Object)");

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .to(sink)
                .report("Trust Boundary Violation here!!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
