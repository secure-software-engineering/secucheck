package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.NullInThroughMethodCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class NullInThroughMethodCaseTest {
    @Test(expected = NullPointerException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        NullInThroughMethodCaseSpec spec = new NullInThroughMethodCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);
        fluentTQLUserInterface.getFluentTQLSpecification();   //Todo: This gives NullPointerException
    }
}
