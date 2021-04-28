package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.XSS.CWE79;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks.ServletSinks;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sources.ServletSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal FluentTQL specification for Reflexive-XSS.
 *
 * @author Ranjith Krishnamurthy
 */
public class ReflexiveXSSSpec implements FluentTQLUserInterface {
    /**
     * encodeForHTML is a OWASP sanitizer that encodes the HTML related data. Therefore, flow should go through this method to avoid vulnerability.
     */
    public Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForHTML(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * decodeForHTML is a OWASP de-sanitizer that decodes the HTML related data back to HTML entities. Therefore, flow should not go though this method to avoid
     * vulnerability.
     */
    public Method deSanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String decodeForHTML(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("ReflexiveXSS")
                .from(ServletSources.servletSources)
                .through(deSanitizer)
                .notThrough(sanitizer)
                .to(ServletSinks.servletSinks)
                .report("Reflexive XSS - CWE79 !")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
