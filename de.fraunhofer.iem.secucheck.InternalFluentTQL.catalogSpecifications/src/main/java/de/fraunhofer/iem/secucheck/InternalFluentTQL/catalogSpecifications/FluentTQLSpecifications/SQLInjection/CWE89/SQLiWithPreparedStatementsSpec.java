package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.SQLInjection.CWE89;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sinks.PreparedStatementSinks;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FuentTQLRepositories.Sources.ServletSources;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
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
 * Internal FluentTQL specification for SQL-Injection with prepared statements.
 *
 * @author Ranjith Krishnamurthy
 */
@FluentTQLSpecificationClass
@ImportAndProcessOnlyStaticFields(classList = {ServletSources.class, PreparedStatementSinks.class})
public class SQLiWithPreparedStatementsSpec implements FluentTQLUserInterface {
    /**
     * encodeForSQL is a OWASP sanitizer that encodes the SQL related data. Therefore, flow should go through this method to avoid vulnerability.
     */
    public Method sanitizer = new MethodConfigurator("org.owasp.esapi.Encoder: java.lang.String encodeForSQL(org.owasp.esapi.codecs.Codec,java.lang.String)")
            .in().param(1)
            .out().returnValue()
            .configure();

    public Method requiredPropagator1 = new MethodConfigurator("java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    public Method requiredPropagator2 = new MethodConfigurator("java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String,int)")
            .in().param(0)
            .out().returnValue()
            .configure();

    public Method requiredPropagator3 = new MethodConfigurator("java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String,java.lang.String[])")
            .in().param(0)
            .out().returnValue()
            .configure();

    public Method requiredPropagator4 = new MethodConfigurator("java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String,int[])")
            .in().param(0)
            .out().returnValue()
            .configure();

    public Method requiredPropagator5 = new MethodConfigurator("java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String,int,int)")
            .in().param(0)
            .out().returnValue()
            .configure();

    public Method requiredPropagator6 = new MethodConfigurator("java.sql.Connection: java.sql.PreparedStatement prepareStatement(java.lang.String,int,int,int)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * This MethodSet contains the 6 required propagator that has to be called to create a PreparedStatement object.
     */
    public MethodSet requiredPropagator = new MethodSet("requiredPropagator")
            .addMethod(requiredPropagator1)
            .addMethod(requiredPropagator2)
            .addMethod(requiredPropagator3)
            .addMethod(requiredPropagator4)
            .addMethod(requiredPropagator5)
            .addMethod(requiredPropagator6);

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery myTF = new TaintFlowQueryBuilder("SQLiWithPreparedStmt")
                .from(ServletSources.servletSources)
                .notThrough(sanitizer)
                .through(requiredPropagator)
                .to(PreparedStatementSinks.prepSinks)
                .report("SQL-Injection, even though its prepared statement - CWE89!!!")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(myTF);

        return myFluentTQLSpecs;
    }
}
