package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.NullInFromMethodCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class NullInFromMethodCaseTest {
    @Test(expected = NullPointerException.class)
    public void test() throws FluentTQLException {
        NullInFromMethodCaseSpec spec = new NullInFromMethodCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);
        fluentTQLUserInterface.getFluentTQLSpecification();   //Todo: This gives NullPointerException
    }
}
