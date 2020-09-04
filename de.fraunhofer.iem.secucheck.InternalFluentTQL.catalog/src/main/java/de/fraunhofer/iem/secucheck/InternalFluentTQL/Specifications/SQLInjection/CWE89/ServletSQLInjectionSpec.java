package de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.SQLInjection.CWE89;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.Sinks.SQLSinks;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.Specifications.Sources.ServletSources;
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
 * Internal FluentTQL specification for SQL-Injection in servlet application.
 *
 * @author Ranjith Krishnamurthy
 */
public class ServletSQLInjectionSpec implements FluentTQLUserInterface {
    /**
     * encodeForSQL is a OWASP sanitizer that encodes the SQL related data. Therefore, flow should go through this method to avoid vulnerability.
     */
    Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForSQL(org.owasp.esapi.codecs.Codec, java.lang.String)")
            .in().param(1)
            .out().returnValue().configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder()
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .to(SQLSinks.sqlSinks)
                .report("Servlet SQL-Injection - CWE89!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
