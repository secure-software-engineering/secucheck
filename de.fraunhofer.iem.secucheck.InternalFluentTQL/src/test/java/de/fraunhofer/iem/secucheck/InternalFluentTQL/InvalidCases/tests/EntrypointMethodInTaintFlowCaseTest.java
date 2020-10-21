package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.EntrypointMethodInTaintFlowCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.InvalidFluentTQLSpecificationException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class EntrypointMethodInTaintFlowCaseTest {
    @Test(expected = InvalidFluentTQLSpecificationException.class)
    public void test1() throws FluentTQLException {
        EntrypointMethodInTaintFlowCaseSpec spec = new EntrypointMethodInTaintFlowCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);   //Todo: This gives InvalidFluentTQLSpecificationException
        fluentTQLUserInterface.getFluentTQLSpecification();
    }
}
