package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.LogInjection.CWE117;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sinks.LogInjectionSinks;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sources.ServletSources;
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
 * Internal FluentTQL specification for Log-Injection.
 *
 * @author Ranjith Krishnamurthy
 */
@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {ServletSources.class, LogInjectionSinks.class})
public class LogInjectionSpec implements FluentTQLUserInterface {
    /**
     * encodeForURL is a OWASP sanitizer that encodes the URL. This encodes all the new line and carriage return,
     * therefore Log-Injection will be avoided.
     */
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method sanitizer = new MethodSelector("org.owasp.esapi.Encoder: java.lang.String encodeForURL(java.lang.String)");

    /**
     * decodeFromURL is a OWASP de-sanitizer that decodes the URL. This decodes all the new line and carriage return,
     * therefore this method must be avoided before calling Log-Injection sinks.
     */
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method deSanitizer = new MethodSelector("org.owasp.esapi.Encoder: java.lang.String decodeFromURL(java.lang.String)");

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
