package de.fraunhofer.iem.secucheck.fluenttql.ValidCases.specifications;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.*;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.*;

@FluentTQLRepositoryClass
public class MethodDeclarationForNoSQLInjection {

    //First source that takes userName from the user.
    @OutFlowReturnValue
    public static Method source1 = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getUserName()");

    //Second source that takes old password from the user.
    @OutFlowReturnValue
    public static Method source2 = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getOldPassword()");

    //Third source that takes new password from the user.
    @OutFlowReturnValue
    public static Method source3 = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getNewPassword()");

    //sanitizeForMongoDB is user defined simple sanitizer for mongodb.
    @InFlowParam(parameterID = {0})
    @OutFlowReturnValue
    public static Method sanitizer = new MethodSelector("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String sanitizeForMongoDB(java.lang.String)");

    //put is a method that the data flow has to go through after sanitizer to form a filer to update the password.
    //If the data flow goes through this method before sanitizer then there will be a security vulnerability.
    @InFlowParam(parameterID = {1})
    @OutFlowParam(parameterID = {1})
    @OutFlowThisObject
    public static Method requiredPropagator1 = new MethodSelector("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String, java.lang.String)");

    //this put is a method that the data flow has to go through after sanitizer to form a new BasicDBObject password to update the password in mongodb.
    //If the data flow goes through this method before sanitizer then there will be a security vulnerability.
    @InFlowParam(parameterID = {1})
    @OutFlowThisObject
    public static Method requiredPropagator2 = new MethodSelector("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String, com.mongodb.BasicDBObject)");

    //updateOne is a sink that updates the password.
    @InFlowParam(parameterID = {0, 1})
    public static Method sink = new MethodSelector("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable updateOne(com.mongodb.BasicDBObject, com.mongodb.BasicDBObject)");

}
