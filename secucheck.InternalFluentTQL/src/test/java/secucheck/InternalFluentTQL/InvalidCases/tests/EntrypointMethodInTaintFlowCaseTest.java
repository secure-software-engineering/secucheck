package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.EntrypointMethodInTaintFlowCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.dsl.exception.InvalidFluentTQLSpecificationException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
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
