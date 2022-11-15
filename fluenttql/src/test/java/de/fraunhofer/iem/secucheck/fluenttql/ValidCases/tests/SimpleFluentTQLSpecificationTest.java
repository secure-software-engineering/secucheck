package de.fraunhofer.iem.secucheck.fluenttql.ValidCases.tests;

import de.fraunhofer.iem.secucheck.fluenttql.ValidCases.specifications.SimpleFluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput.Parameter;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput.Return;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.TaintFlowPackage.TaintFlow;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SimpleFluentTQLSpecificationTest {
    @Test
    public void test1() throws FluentTQLException {
        SimpleFluentTQLSpecification simpleFluentTQLSpecification = new SimpleFluentTQLSpecification();

        List<FluentTQLSpecification> fluentTQLSpecifications = new ProcessAnnotatedClass().processFluentTQLSpecificationClassAnnotation(
                simpleFluentTQLSpecification
        ).getFluentTQLSpecification();

        Assert.assertEquals(1, fluentTQLSpecifications.size());
        Assert.assertTrue(fluentTQLSpecifications.get(0) instanceof TaintFlowQuery);

        TaintFlowQuery taintFlowQuery = (TaintFlowQuery) fluentTQLSpecifications.get(0);
        Assert.assertEquals(LOCATION.SOURCEANDSINK, taintFlowQuery.getReportLocation());
        Assert.assertEquals("There is a SQL Injection - CWE89!!!", taintFlowQuery.getReportMessage());

        List<TaintFlow> taintFlows = taintFlowQuery.getTaintFlows();

        Assert.assertEquals(1, taintFlows.size());

        TaintFlow taintFlow = taintFlows.get(0);

        Assert.assertTrue(taintFlow.getFrom() instanceof Method);
        Assert.assertEquals(0, taintFlow.getThrough().size());
        Assert.assertEquals(1, taintFlow.getNotThrough().size());
        Assert.assertTrue(taintFlow.getTo() instanceof Method);

        Method from = (Method) taintFlow.getFrom();
        Method notThrough = (Method) taintFlow.getNotThrough().get(0);
        Method to = (Method) taintFlow.getTo();

        // From
        Assert.assertEquals("java.util.Scanner: java.lang.String nextLine()", from.getSignature());
        Assert.assertEquals(0, from.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, from.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(from.getOutputDeclaration().getOutputs().get(0) instanceof Return);

        // NotThrough
        Assert.assertEquals("org.owasp.html.PolicyFactory: java.lang.String sanitize(java.lang.String)", notThrough.getSignature());
        Assert.assertEquals(1, notThrough.getInputDeclaration().getInputs().size());
        Assert.assertEquals(1, notThrough.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(notThrough.getOutputDeclaration().getOutputs().get(0) instanceof Return);
        Assert.assertTrue(notThrough.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) notThrough.getInputDeclaration().getInputs().get(0)).getParameterId());

        //To
        Assert.assertEquals("java.sql.Statement: java.sql.ResultSet executeQuery(java.lang.String)", to.getSignature());
        Assert.assertEquals(1, to.getInputDeclaration().getInputs().size());
        Assert.assertEquals(0, to.getOutputDeclaration().getOutputs().size());
        Assert.assertTrue(to.getInputDeclaration().getInputs().get(0) instanceof Parameter);
        Assert.assertEquals(0, ((Parameter) to.getInputDeclaration().getInputs().get(0)).getParameterId());
    }
}
