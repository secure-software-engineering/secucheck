package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.NotAFluentTQLSpecificationCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.dsl.exception.NotAFluentTQLSpecificationClassException;
import org.junit.Test;

public class NotAFluentTQLSpecificationCaseTest {
    @Test(expected = NotAFluentTQLSpecificationClassException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        NotAFluentTQLSpecificationCaseSpec spec = new NotAFluentTQLSpecificationCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();


        //Todo: This gives NotAFluentTQLSpecificationClassException
        processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);
    }
}
