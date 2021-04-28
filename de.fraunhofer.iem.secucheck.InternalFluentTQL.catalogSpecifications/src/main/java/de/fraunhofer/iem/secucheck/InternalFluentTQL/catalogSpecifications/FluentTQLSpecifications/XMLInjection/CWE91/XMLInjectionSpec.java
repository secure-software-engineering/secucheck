package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.XMLInjection.CWE91;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks.XMLSinks;
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
 * Internal FluentTQL specification for XML-Injection.
 *
 * @author Ranjith Krishnamurthy
 */
public class XMLInjectionSpec implements FluentTQLUserInterface {
    /**
     * encodeForXML is a OWASP sanitizer that encodes the data to avoid XML-Injection.
     */
    public Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForXML(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("XMLi")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .to(XMLSinks.sinksXMLinjection)
                .report("XML-Injection here!!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
