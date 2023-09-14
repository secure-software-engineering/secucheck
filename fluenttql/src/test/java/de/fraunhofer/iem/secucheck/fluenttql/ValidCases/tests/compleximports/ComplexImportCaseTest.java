package de.fraunhofer.iem.secucheck.fluenttql.ValidCases.tests.compleximports;

import de.fraunhofer.iem.secucheck.fluenttql.ValidCases.specifications.compleximports.ComplexImportCaseSpec;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.CONSTANTS.LOCATION;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.Return;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.ThisObject;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.Query.TaintFlowQuery;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.SpecificationInterface.FluentTQLUserInterface;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.TaintFlowPackage.TaintFlow;
import org.junit.Assert;
import org.junit.Test;

public class ComplexImportCaseTest {
    @Test
    public void test1() throws FluentTQLException {
        ComplexImportCaseSpec spec = new ComplexImportCaseSpec();

        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);

        TaintFlowQuery taintFlowQuery = (TaintFlowQuery) fluentTQLUserInterface.getFluentTQLSpecification().get(0);

        Assert.assertEquals("This is complex import spec example", taintFlowQuery.getReportMessage());
        Assert.assertEquals(LOCATION.SOURCEANDSINK, taintFlowQuery.getReportLocation());

        TaintFlow taintFlow = taintFlowQuery.getTaintFlows().get(0);

        MethodSet from = (MethodSet) taintFlow.getFrom();
        MethodSet notThrough = (MethodSet) taintFlow.getNotThrough().get(0);
        MethodSet to = (MethodSet) taintFlow.getTo();

        Assert.assertEquals(12, from.getMethods().size());
        Assert.assertEquals(12, notThrough.getMethods().size());
        Assert.assertEquals(6, to.getMethods().size());

        for (int i = 0; i < 12; i++) {
            int count = i + 1;
            Assert.assertEquals(
                    "Test1: java.lang.String source" + count + "()",
                    from.getMethods().get(i).getSignature());
            Assert.assertEquals(0, from.getMethods().get(i).getInputDeclaration().getInputs().size());
            Assert.assertEquals(1, from.getMethods().get(i).getOutputDeclaration().getOutputs().size());
            Assert.assertTrue(from.getMethods().get(i).getOutputDeclaration().getOutputs().get(0) instanceof Return);

            Assert.assertEquals(
                    "Test1: java.lang.String sanitizer" + count + "()",
                    notThrough.getMethods().get(i).getSignature());
            Assert.assertEquals(1, notThrough.getMethods().get(i).getInputDeclaration().getInputs().size());
            Assert.assertEquals(1, notThrough.getMethods().get(i).getOutputDeclaration().getOutputs().size());
            Assert.assertTrue(notThrough.getMethods().get(i).getInputDeclaration().getInputs().get(0) instanceof ThisObject);
            Assert.assertTrue(notThrough.getMethods().get(i).getOutputDeclaration().getOutputs().get(0) instanceof Return);

            if (i < 6) {
                Assert.assertEquals(
                        "Test1: java.lang.String sink" + count + "()",
                        to.getMethods().get(i).getSignature());
                Assert.assertEquals(1, to.getMethods().get(i).getInputDeclaration().getInputs().size());
                Assert.assertEquals(0, to.getMethods().get(i).getOutputDeclaration().getOutputs().size());
                Assert.assertTrue(to.getMethods().get(i).getInputDeclaration().getInputs().get(0) instanceof ThisObject);
            }
        }
    }
}
