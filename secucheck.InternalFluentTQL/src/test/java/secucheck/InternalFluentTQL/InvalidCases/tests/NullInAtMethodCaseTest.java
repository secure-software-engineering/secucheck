package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.NullInAtMethodCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class NullInAtMethodCaseTest {
    @Test(expected = NullPointerException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        NullInAtMethodCaseSpec spec = new NullInAtMethodCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);
        fluentTQLUserInterface.getFluentTQLSpecification();   //Todo: This gives NullPointerException
    }
}
