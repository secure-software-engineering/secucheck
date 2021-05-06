package de.fraunhofer.iem.secucheck.InternalFluentTQL.catalogSpecifications.FluentTQLSpecifications.NoSQLInjection.CWE943;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
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
@FluentTQLSpecificationClass
public class NoSQLInjectionInSpringBoot implements FluentTQLUserInterface {
    /**
     * Source 1
     */
    @OutFlowParam(parameterID = {0})
    public Method source1 = new MethodSelector("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot: org.springframework.http.ResponseEntity getMyInformation(java.lang.String)");

    /**
     * Source 2
     */
    @OutFlowParam(parameterID = {0})
    public Method source2 = new MethodSelector("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot: org.springframework.http.ResponseEntity getMyInformationSafely(java.lang.String)");

    /**
     * getMyInformation and getMyInformationSafely are source, since both take input from user.
     */
    public MethodSet source = new MethodSet("source")
            .addMethod(source1)
            .addMethod(source2);

    /**
     * sanitizeForMongoDB is user defined simple sanitizer for mongodb.
     */
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method sanitizer = new MethodSelector("de.fraunhofer.iem.secucheck.InternalFluentTQL.catalog.NoSQLInjection.CWE943.NoSQLInjectionInSpringBoot: java.lang.String sanitizeForMongoDB(java.lang.String)");

    /**
     * put is a method that the data flow has to go through after sanitizer. If the data flow goes through this method before sanitizer then there will be a security vulnerability.
     */
    @InFlowParam(parameterID = {1})
    @OutFlowThisObject
    public Method requiredPropogator = new MethodSelector("com.mongodb.BasicDBObject: java.lang.Object put(java.lang.Object,java.lang.Object)");

    /**
     * find is a sink that retrieves sensitive information from mongodb.
     */
    @InFlowParam(parameterID = {0})
    public Method sink = new MethodSelector("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable find(org.bson.conversions.Bson)");

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
