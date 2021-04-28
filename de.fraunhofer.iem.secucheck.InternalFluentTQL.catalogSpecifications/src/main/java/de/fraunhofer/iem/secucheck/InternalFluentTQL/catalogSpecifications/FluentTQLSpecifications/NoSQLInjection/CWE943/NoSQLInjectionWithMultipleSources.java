package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.NoSQLInjection.CWE943;

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
 * Internal FluentTQL specification for No-SQL-Injection with multiple sources.
 *
 * @author Ranjith Krishnamurthy
 */
public class NoSQLInjectionWithMultipleSources implements FluentTQLUserInterface {
    /**
     * First source that takes userName from the user.
     */
    public Method source1 = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getUserName()")
            .out().returnValue()
            .configure();

    /**
     * Second source that takes old password from the user.
     */
    public Method source2 = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getOldPassword()")
            .out().returnValue()
            .configure();

    /**
     * Third souce that takes new password from the user.
     */
    public Method source3 = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getNewPassword()")
            .out().returnValue()
            .configure();

    /**
     * sanitizeForMongoDB is user defined simple sanitizer for mongodb.
     */
    public Method sanitizer = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String sanitizeForMongoDB(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * put is a method that the data flow has to go through after sanitizer to form a filer to update the password.
     * If the data flow goes through this method before sanitizer then there will be a security vulnerability.
     */
    public Method requiredPropagator1 = new MethodConfigurator("com.mongodb.BasicDBObject: java.lang.Object put(java.lang.Object,java.lang.Object)")
            .in().param(1)
            .out().thisObject()
            .configure();

    /**
     * This put is a method that the data flow has to go through after sanitizer to form a new BasicDBObject password to update the password in mongodb.
     * If the data flow goes through this method before sanitizer then there will be a security vulnerability.
     */
    public Method requiredPropagator2 = new MethodConfigurator("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String,com.mongodb.BasicDBObject)")
            .in().param(1)
            .out().thisObject()
            .configure();

    /**
     * updateOne is a sink that updates the password.
     */
    public Method sink = new MethodConfigurator("com.mongodb.client.MongoCollection: com.mongodb.client.result.UpdateResult updateOne(org.bson.conversions.Bson,org.bson.conversions.Bson)")
            .in().param(0).param(1)
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery noSQLWithMultipleSourcesSpec = new TaintFlowQueryBuilder("NoSQLiWithMultipleSources")
                .from(source1).notThrough(sanitizer).through(requiredPropagator1).to(sink)
                .and()
                .from(source2).notThrough(sanitizer).through(requiredPropagator1).to(sink)
                .and()
                .from(source3).notThrough(sanitizer).through(requiredPropagator1).to(sink)
                .report("There is a No-SQL-Injection (CWE943) with multiple sources")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(noSQLWithMultipleSourcesSpec);

        return myFluentTQLSpecs;
    }
}
