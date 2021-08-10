package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.ReturnsNullSpecificationCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

public class ReturnsNullSpecificationCaseTest {
    @Test(expected = NullPointerException.class)
    public void ReturnsNullSpecificationCase() throws FluentTQLException {
        ReturnsNullSpecificationCaseSpec spec = new ReturnsNullSpecificationCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);   //Todo: This gives NullPointerException
    }
}
