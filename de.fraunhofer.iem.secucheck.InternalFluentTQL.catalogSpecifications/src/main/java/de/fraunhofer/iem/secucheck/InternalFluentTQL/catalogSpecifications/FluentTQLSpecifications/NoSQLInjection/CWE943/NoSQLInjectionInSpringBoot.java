package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.NoSQLInjection.CWE943;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal FluentTQL specification for NO-SQL-Injection in Spring boot application.
 *
 * @author Ranjith Krishnamurthy
 */
public class NoSQLInjectionInSpringBoot implements FluentTQLUserInterface {
    /**
     * Source 1
     */
    public Method source1 = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot: org.springframework.http.ResponseEntity getMyInformation(java.lang.String)")
            .out().param(0)
            .configure();

    /**
     * Source 2
     */
    public Method source2 = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot: org.springframework.http.ResponseEntity getMyInformationSafely(java.lang.String)")
            .out().param(0)
            .configure();

    /**
     * getMyInformation and getMyInformationSafely are source, since both take input from user.
     */
    public MethodSet source = new MethodSet("source")
            .addMethod(source1)
            .addMethod(source2);

    /**
     * sanitizeForMongoDB is user defined simple sanitizer for mongodb.
     */
    public Method sanitizer = new MethodConfigurator("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot: java.lang.String sanitizeForMongoDB(java.lang.String)")
            .in().param(0)
            .out().returnValue()
            .configure();

    /**
     * put is a method that the data flow has to go through after sanitizer. If the data flow goes through this method before sanitizer then there will be a security vulnerability.
     */
    public Method requiredPropogator = new MethodConfigurator("com.mongodb.BasicDBObject: java.lang.Object put(java.lang.Object,java.lang.Object)")
            .in().param(1)
            .out().thisObject()
            .configure();

    /**
     * find is a sink that retrieves sensitive information from mongodb.
     */
    public Method sink = new MethodConfigurator("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable find(org.bson.conversions.Bson)")
            .in().param(0)
            .configure();

    /**
     * Returns the Internal FluentTQL specification
     *
     * @return Internal FluentTQL specification
     */
    public List<FluentTQLSpecification> getFluentTQLSpecification() {
        TaintFlowQuery noSQLInSpringBootSpec = new TaintFlowQueryBuilder("NoSQLiInSpring")
                .from(source)                            //source methods
                .notThrough(sanitizer)                    //sanitizer
                .through(requiredPropogator)            //requires propagator
                .to(sink)                                //sink methods
                .report("No-SQL-Injection - CWE943!")        //report message
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(noSQLInSpringBootSpec);

        return myFluentTQLSpecs;
    }
}
