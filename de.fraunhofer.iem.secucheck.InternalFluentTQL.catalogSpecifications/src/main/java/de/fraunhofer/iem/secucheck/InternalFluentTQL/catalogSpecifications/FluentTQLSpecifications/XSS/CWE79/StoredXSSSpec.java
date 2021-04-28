package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.XSS.CWE79;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks.ServletSinks;
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
 * Internal FluentTQL specification for Stored-XSS.
 * For Stored XSS below are the two workarounds to have fluentTQL specifications.
 * 1. Use the SQL Injection fluentTQL specification to avoid storing the malicious comments into database.
 * 2. Use the below specification to avoid sending the malicious comments from database to client.
 * <p>
 * For the actual solution, there is a need to changing the fluentTQL language in future.
 *
 * @author Ranjith Krishnamurthy
 */
public class StoredXSSSpec implements FluentTQLUserInterface {
    /**
     * getLastComment is a source that returns the sensitive data from the database.
     */
    public Method source = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.XSS.CWE79.DatabaseForStoredXSS: java.lang.String getLastComment()")
            .out().returnValue()
            .configure();

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
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("StoredXSS")
                .from(source)
                .through(deSanitizer)
                .notThrough(sanitizer)
                .to(ServletSinks.servletSinks)
                .report("Stored XSS - CWE79!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
