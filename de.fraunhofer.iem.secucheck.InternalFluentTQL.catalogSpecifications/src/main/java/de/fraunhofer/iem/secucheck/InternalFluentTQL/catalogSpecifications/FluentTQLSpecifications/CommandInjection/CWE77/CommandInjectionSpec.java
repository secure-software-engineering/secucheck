package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.CommandInjection.CWE77;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sources.ServletSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
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
@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {ServletSources.class})
public class CommandInjectionSpec implements FluentTQLUserInterface {

    /**
     * encodeForOS is OWASP sanitizer that encodes the string to avoid command injection. This method
     * has to be called before the requiredPropagator to avoid the security vulnerability.
     */
    @InFlowParam(parameterID = {1})
    @OutFlowReturnValue
    public Method sanitizer = new MethodSelector("org.owasp.esapi.Encoder: java.lang.String encodeForOS(org.owasp.esapi.codecs.Codec, java.lang.String)");

    /**
     * ProcessBuilder constructor should be called to run the command, but first arguments should be sanitized to
     * avoid the security vulnerability.
     */
    @InFlowParam(parameterID = {0})
    @OutFlowThisObject
    public Method requiredPropagator = new MethodSelector("java.lang.ProcessBuilder: void <init>(java.lang.String[])");

    /**
     * Sink.
     */
    @InFlowThisObject
    public Method sink = new MethodSelector("java.lang.ProcessBuilder: java.lang.Process start()");

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
