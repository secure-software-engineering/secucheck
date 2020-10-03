package de.fraunhofer.iem.secucheck.InternalFluentTQL;

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

@FluentTQLSpecificationClass
public class  FluentTQLSpecificationTestForNoSQLInjection implements FluentTQLUserInterface {

    //First source that takes userName from the user.
//    @OutFlowReturnValue
//    public Method source1 = new MethodImpl("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getUserName()");

    @ImportAndProcessAnnotation
    public SimpleFluentTQLSpecification simpleFluentTQLSpecification = new SimpleFluentTQLSpecification();

    //Second source that takes old password from the user.
    @OutFlowReturnValue
    public Method source2 = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getOldPassword()");

    //Third souce that takes new password from the user.
    @OutFlowReturnValue
    public Method source3 = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getNewPassword()");

    //sanitizeForMongoDB is user defined simple sanitizer for mongodb.
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public Method sanitizer = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String sanitizeForMongoDB(java.lang.String)");

    //put is a method that the data flow has to go through after sanitizer to form a filer to update the password.
    //If the data flow goes through this method before sanitizer then there will be a security vulnerability.
    @InFlowParam(parameterID = {1})
    @OutFlowParam(parameterID = {1})
    @OutFlowThisObject
    public Method requiredPropagator1 = new MethodSelector("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String, java.lang.String)");

    //this put is a method that the data flow has to go through after sanitizer to form a new BasicDBObject password to update the password in mongodb.
    //If the data flow goes through this method before sanitizer then there will be a security vulnerability.
    @InFlowParam(parameterID = {1})
    @OutFlowThisObject
    public Method requiredPropagator2 = new MethodSelector("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String, com.mongodb.BasicDBObject)");

    //updateOne is a sink that updates the password.
    @InFlowParam(parameterID = {0,1})
    public Method sink = new MethodSelector("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable updateOne(com.mongodb.BasicDBObject, com.mongodb.BasicDBObject)");

    public List<FluentTQLSpecification> getFluentTQLSpecification() {

        TaintFlowQuery noSQLInjection = new TaintFlowQueryBuilder()
                .from(simpleFluentTQLSpecification.myMethodSet).notThrough(sanitizer).through(requiredPropagator1).to(sink)
                .and()
                .from(source2).notThrough(sanitizer).through(requiredPropagator1).to(sink)
                .and()
                .from(source3).notThrough(sanitizer).through(requiredPropagator2).to(sink)
                .report("There is a No-SQL-Injection (CWE943) with multiple sources")
                .at(LOCATION.SOURCEANDSINK)
                .build();

        List<FluentTQLSpecification> myFluentTQLSpecs = new ArrayList<FluentTQLSpecification>();
        myFluentTQLSpecs.add(noSQLInjection);

        return myFluentTQLSpecs;
    }
}
