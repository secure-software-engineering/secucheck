package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.LogInjection.CWE117;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks.LogInjectionSinks;
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
 * Internal FluentTQL specification for Log-Injection.
 *
 * @author Ranjith Krishnamurthy
 */
public class LogInjectionSpec implements FluentTQLUserInterface {
    /**
     * encodeForURL is a OWASP sanitizer that encodes the URL. This encodes all the new line and carriage return,
     * therefore Log-Injection will be avoided.
     */
    public Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForURL(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * decodeFromURL is a OWASP de-sanitizer that decodes the URL. This decodes all the new line and carriage return,
     * therefore this method must be avoided before calling Log-Injection sinks.
     */
    public Method deSanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String decodeFromURL(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery logInjectionSpec1 = new TaintFlowQueryBuilder("LogInjectionWithDeSanitizer")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .through(deSanitizer)
                .to(LogInjectionSinks.logInjectionSinks)
                .report("Log-Injection CWE-117!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        TaintFlowQuery logInjectionSpec2 = new TaintFlowQueryBuilder("LogInjectionWithoutDesanitizer")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .to(LogInjectionSinks.logInjectionSinks)
                .report("Log-Injection CWE-117!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(logInjectionSpec1);
        myFluentTQLSpecs.add(logInjectionSpec2);

        return myFluentTQLSpecs;
    }
}
