package de.fraunhofer.iem.secucheck.fluenttql;

import java.util.ArrayList;
import java.util.List;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.TaintFlowQueryBuilder;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

public class FluentTQLSpecificationTestForNoSQLInjectionWithMethodSign implements FluentTQLUserInterface {
	public List<FluentTQLSpecification> getFluentTQLSpecification() {
        //First source that takes userName from the user.
		MethodSignature source1signature = new MethodSignatureBuilder()
				.atClass("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources").returns("java.lang.String").named("getUserName").parameter("")
				.configure();
        Method source1 = new MethodConfigurator(source1signature)
                .out().returnValue()
                .configure();

        //Second source that takes old password from the user.
        MethodSignature source2signature = new MethodSignatureBuilder()
				.atClass("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources").returns("java.lang.String").named("getOldPassword").parameter("")
				.configure();
        Method source2 = new MethodConfigurator(source2signature)
                .out().returnValue()
                .configure();

        //Third source that takes new password from the user.
        MethodSignature source3signature = new MethodSignatureBuilder()
				.atClass("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources").returns("java.lang.String").named("getNewPassword").parameter("")
				.configure();
        Method source3 = new MethodConfigurator(source3signature)
                .out().returnValue()
                .configure();

        //sanitizeForMongoDB is user defined simple sanitizer for MongoDB.
        MethodSignature sanitizersignature = new MethodSignatureBuilder()
				.atClass("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources").returns("java.lang.String").named("sanitizeForMongoDB").parameter("java.lang.String")
				.configure();
        Method sanitizer = new MethodConfigurator(sanitizersignature)
                .in().param(0)
                .out().returnValue()
                .configure();

        //put is a method that the data flow has to go through after sanitizer to form a filer to update the password.
        //If the data flow goes through this method before sanitizer then there will be a security vulnerability.
        MethodSignature requiredPropagator1signature = new MethodSignatureBuilder()
				.atClass("com.mongodb.BasicDBObject").returns("com.mongodb.BasicDBObject").named("put").parameter("java.lang.String, java.lang.String")
				.configure();
        Method requiredPropagator1 = new MethodConfigurator(requiredPropagator1signature)
                .in().param(1)
                .out().param(1).thisObject()
                .configure();

        //this put is a method that the data flow has to go through after sanitizer to form a new BasicDBObject password to update the password in MongoDB.
        //If the data flow goes through this method before sanitizer then there will be a security vulnerability.
        MethodSignature requiredPropagator2signature = new MethodSignatureBuilder()
				.atClass("com.mongodb.BasicDBObject").returns("com.mongodb.BasicDBObject").named("put").parameter("java.lang.String, com.mongodb.BasicDBObject")
				.configure();
        Method requiredPropagator2 = new MethodConfigurator(requiredPropagator2signature)
                .in().param(1)
                .out().thisObject()
                .configure();

        //updateOne is a sink that updates the password.
        MethodSignature sinksignature = new MethodSignatureBuilder()
				.atClass("com.mongodb.client.MongoCollection").returns("com.mongodb.client.FindIterable").named("updateOne").parameter("com.mongodb.BasicDBObject, com.mongodb.BasicDBObject")
				.configure();
        Method sink = new MethodConfigurator(sinksignature)
                .in().param(0).param(1)
                .configure();

        TaintFlowQuery noSQLInjection = new TaintFlowQueryBuilder("NoSQL")
                .from(source1).notThrough(sanitizer).through(requiredPropagator1).to(sink)
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
