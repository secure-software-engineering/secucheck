package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.LDAPInjection.CWE90;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.Sinks.LdapSinks;
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
 * Internal FluentTQL specification for LDAP-Injection.
 *
 * @author Ranjith Krishnamurthy
 */
public class LDAPInjectionSpec implements FluentTQLUserInterface {
    /**
     * encodeForLDAP is OWASP sanitizer that encodes the string to avoid LDAP-injection.
     */
    public Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForLDAP(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery ldapInjectionSpecification = new TaintFlowQueryBuilder("LDAPInjection_CWE90")
                .from(ServletSources.servletSources).notThrough(sanitizer)
                .to(LdapSinks.sinksLdapinjection)
                .report("LDAP-Injection CWE-90!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(ldapInjectionSpecification);

        return myFluentTQLSpecs;
    }
}
