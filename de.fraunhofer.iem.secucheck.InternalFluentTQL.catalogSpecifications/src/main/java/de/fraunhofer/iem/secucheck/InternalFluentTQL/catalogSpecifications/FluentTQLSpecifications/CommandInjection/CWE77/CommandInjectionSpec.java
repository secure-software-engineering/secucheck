package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.CommandInjection.CWE77;

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
 * Internal FluentTQL specification for Command-Injection.
 *
 * @author Ranjith Krishnamurthy
 */
public class CommandInjectionSpec implements FluentTQLUserInterface {

    /**
     * encodeForOS is OWASP sanitizer that encodes the string to avoid command injection. This method
     * has to be called before the requiredPropagator to avoid the security vulnerability.
     */
    public Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForOS(org.owasp.esapi.codecs.Codec,java.lang.String)")
            .in().param(1)
            .out().returnValue()
            .configure();

    /**
     * ProcessBuilder constructor should be called to run the command, but first arguments should be sanitized to
     * avoid the security vulnerability.
     */
    public Method requiredPropagator = new MethodConfigurator("java.lang.ProcessBuilder: void <init>(java.lang.String[])")
            .in().param(0)
            .out().thisObject()
            .configure();

    /**
     * Sink.
     */
    public Method sink = new MethodConfigurator("java.lang.ProcessBuilder: java.lang.Process start()")
            .in().thisObject()
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery commandInjectionSpecification = new TaintFlowQueryBuilder("CommandInjection_CWE77")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .through(requiredPropagator)
                .to(sink)
                .report("Command Injection - CWE77!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(commandInjectionSpecification);

        return myFluentTQLSpecs;
    }
}
