package de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.OpenRedirectAttack.CWE601;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.Sources.ServletSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.QueriesSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal FluentTQL specification for Open-Redirect attack.
 *
 * @author Ranjith Krishnamurthy
 */
public class OpenRedirectAttackSpec implements FluentTQLUserInterface {
    /**
     * Source 4
     */
    Method source4 = new MethodConfigurator("javax.servlet.http.HttpServletRequest: javax.servlet.RequestDispatcher getRequestDispatcher(java.lang.String)")
            .out().returnValue().configure();
    /**
     * redirectTable is a simple redirection table that performs the whitelist input validation.
     */
    Method sanitizer = new MethodConfigurator("catalog.OpenRedirect.CWE601: java.lang.String redirectTable(java.lang.String)")
            .in().param(0)
            .out().returnValue().configure();

    /**
     * Sink 1
     */
    Method sink1 = new MethodConfigurator("javax.servlet.http.HttpServletResponse: void sendRedirect(java.lang.String)")
            .in().param(0).configure();

    /**
     * Sink 2
     */
    Method sink2 = new MethodConfigurator("javax.servlet.RequestDispatcher: void forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse)")
            .in().thisObject().configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery tf1 = new TaintFlowQueryBuilder()
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .to(sink1)
                .report("Open-Redirect - CWE601!")
                .at(LOCATION.SOURCE)
                .build();

        TaintFlowQuery tf2 = new TaintFlowQueryBuilder()
                .from(source4)
                .notThrough(sanitizer)
                .to(sink2)
                .report("Open-Redirect - CWE601!!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();


        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(
                new QueriesSet("OpenRedirectAttack")
                        .addTaintFlowQuery(tf1)
                        .addTaintFlowQuery(tf2)
        );

        return myFluentTQLSpecs;
    }
}
