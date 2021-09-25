package de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.ValidCases.specifications.FluentTQLSpecificationTestForNoSQLInjection;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Parameter;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Return;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.TaintFlow;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FluentTQLSpecificationTestForNoSQLInjectionTest {
    @Test
    public void test1() throws FluentTQLException {
        FluentTQLSpecificationTestForNoSQLInjection simpleFluentTQLSpecification = new FluentTQLSpecificationTestForNoSQLInjection();

        List<FluentTQLSpecification> fluentTQLSpecifications = new ProcessAnnotatedClass().processFluentTQLSpecificationClassAnnotation(
                simpleFluentTQLSpecification
        ).getFluentTQLSpecification();

        Assert.assertEquals(1, fluentTQLSpecifications.size());
        Assert.assertTrue(fluentTQLSpecifications.get(0) instanceof TaintFlowQuery);

        TaintFlowQuery taintFlowQuery = (TaintFlowQuery) fluentTQLSpecifications.get(0);
        Assert.assertEquals(LOCATION.SOURCE, taintFlowQuery.getReportLocation());
        Assert.assertEquals("There is a No-SQL-Injection (CWE943) with multiple sources", taintFlowQuery.getReportMessage());

        List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();

        Assert.assertEquals(3, taintFlows.size());

        TaintFlow taintFlow = taintFlows.get(0);

        // First TaintFlow
        Assert.assertTrue(taintFlow.getFrom() instanceof Method);
        Assert.assertEquals(1, taintFlow.getThrough().size());
        Assert.assertEquals(1, taintFlow.getNotThrough().size());
        Assert.assertTrue(taintFlow.getTo() instanceof Method);

        Method from = (Method) taintFlow.getFrom();
        Method notThrough = (Method) taintFlow.getNotThrough().get(0);
        Method through = (Method) taintFlow.getThrough().get(0);
        Method to = (Method) taintFlow.getTo();

        // From
        Assert.assertEquals("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getUserName()", from.getSignature());
        Assert.assertEquals(0, from.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, from.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(from.getOutputDeclaration().getOutputs().get(0) instanceof Return);

        // notThrough
        Assert.assertEquals("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String sanitizeForMongoDB(java.lang.String)", notThrough.getSignature());
        Assert.assertEquals(1, notThrough.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, notThrough.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(notThrough.getOutputDeclaration().getOutputs().get(0) instanceof Return);
        Assert.assertTrue(notThrough.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) notThrough.getInputDeclaration().getInputs().get(0)).getParameterId());

        //Through
        Assert.assertEquals("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String,java.lang.String)", through.getSignature());
        Assert.assertEquals(1, through.getInputDeclaration().getInputs().size());
        Assert.assertEquals(2, through.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(through.getOutputDeclaration().getOutputs().get(0) instanceof Parameter);
        Assert.assertTrue(through.getOutputDeclaration().getOutputs().get(1) instanceof ThisObject);
        Assert.assertTrue(through.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(1, ((Parameter) through.getInputDeclaration().getInputs().get(0)).getParameterId());
        Assert.assertEquals(1, ((Parameter) through.getOutputDeclaration().getOutputs().get(0)).getParameterId());

        //To
        Assert.assertEquals("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable updateOne(com.mongodb.BasicDBObject,com.mongodb.BasicDBObject)", to.getSignature());
        Assert.assertEquals(2, to.getInputDeclaration().getInputs().size());
        Assert.assertEquals(0, to.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(1) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) to.getInputDeclaration().getInputs().get(0)).getParameterId());
        Assert.assertEquals(1, ((Parameter) to.getInputDeclaration().getInputs().get(1)).getParameterId());

        taintFlow = taintFlows.get(1);

        // Second TaintFlow
        Assert.assertTrue(taintFlow.getFrom() instanceof Method);
        Assert.assertEquals(1, taintFlow.getThrough().size());
        Assert.assertEquals(1, taintFlow.getNotThrough().size());
        Assert.assertTrue(taintFlow.getTo() instanceof Method);

        from = (Method) taintFlow.getFrom();
        notThrough = (Method) taintFlow.getNotThrough().get(0);
        through = (Method) taintFlow.getThrough().get(0);
        to = (Method) taintFlow.getTo();

        // From
        Assert.assertEquals("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getOldPassword()", from.getSignature());
        Assert.assertEquals(0, from.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, from.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(from.getOutputDeclaration().getOutputs().get(0) instanceof Return);

        // notThrough
        Assert.assertEquals("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String sanitizeForMongoDB(java.lang.String)", notThrough.getSignature());
        Assert.assertEquals(1, notThrough.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, notThrough.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(notThrough.getOutputDeclaration().getOutputs().get(0) instanceof Return);
        Assert.assertTrue(notThrough.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) notThrough.getInputDeclaration().getInputs().get(0)).getParameterId());

        //Through
        Assert.assertEquals("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String,java.lang.String)", through.getSignature());
        Assert.assertEquals(1, through.getInputDeclaration().getInputs().size());
        Assert.assertEquals(2, through.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(through.getOutputDeclaration().getOutputs().get(0) instanceof Parameter);
        Assert.assertTrue(through.getOutputDeclaration().getOutputs().get(1) instanceof ThisObject);
        Assert.assertTrue(through.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(1, ((Parameter) through.getInputDeclaration().getInputs().get(0)).getParameterId());
        Assert.assertEquals(1, ((Parameter) through.getOutputDeclaration().getOutputs().get(0)).getParameterId());

        //To
        Assert.assertEquals("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable updateOne(com.mongodb.BasicDBObject,com.mongodb.BasicDBObject)", to.getSignature());
        Assert.assertEquals(2, to.getInputDeclaration().getInputs().size());
        Assert.assertEquals(0, to.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(1) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) to.getInputDeclaration().getInputs().get(0)).getParameterId());
        Assert.assertEquals(1, ((Parameter) to.getInputDeclaration().getInputs().get(1)).getParameterId());

        taintFlow = taintFlows.get(2);

        // Third TaintFlow
        Assert.assertTrue(taintFlow.getFrom() instanceof Method);
        Assert.assertEquals(1, taintFlow.getThrough().size());
        Assert.assertEquals(1, taintFlow.getNotThrough().size());
        Assert.assertTrue(taintFlow.getTo() instanceof Method);

        from = (Method) taintFlow.getFrom();
        notThrough = (Method) taintFlow.getNotThrough().get(0);
        through = (Method) taintFlow.getThrough().get(0);
        to = (Method) taintFlow.getTo();

        // From
        Assert.assertEquals("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String getNewPassword()", from.getSignature());
        Assert.assertEquals(0, from.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, from.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(from.getOutputDeclaration().getOutputs().get(0) instanceof Return);

        // notThrough
        Assert.assertEquals("catalog.NoSQLInjection.CWE943.NoSQLInjectionWithTwoSources: java.lang.String sanitizeForMongoDB(java.lang.String)", notThrough.getSignature());
        Assert.assertEquals(1, notThrough.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, notThrough.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(notThrough.getOutputDeclaration().getOutputs().get(0) instanceof Return);
        Assert.assertTrue(notThrough.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) notThrough.getInputDeclaration().getInputs().get(0)).getParameterId());

        //Through
        Assert.assertEquals("com.mongodb.BasicDBObject: com.mongodb.BasicDBObject put(java.lang.String,com.mongodb.BasicDBObject)", through.getSignature());
        Assert.assertEquals(1, through.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, through.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(through.getOutputDeclaration().getOutputs().get(0) instanceof ThisObject);
        Assert.assertTrue(through.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(1, ((Parameter) through.getInputDeclaration().getInputs().get(0)).getParameterId());

        //To
        Assert.assertEquals("com.mongodb.client.MongoCollection: com.mongodb.client.FindIterable updateOne(com.mongodb.BasicDBObject,com.mongodb.BasicDBObject)", to.getSignature());
        Assert.assertEquals(2, to.getInputDeclaration().getInputs().size());
        Assert.assertEquals(0, to.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(1) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) to.getInputDeclaration().getInputs().get(0)).getParameterId());
        Assert.assertEquals(1, ((Parameter) to.getInputDeclaration().getInputs().get(1)).getParameterId());
    }
}
