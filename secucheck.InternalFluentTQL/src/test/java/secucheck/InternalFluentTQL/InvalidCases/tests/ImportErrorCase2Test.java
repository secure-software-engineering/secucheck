package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.ImportErrorCase2Spec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.dsl.exception.ImportAndProcessAnnotationException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class ImportErrorCase2Test {
    @Test(expected = ImportAndProcessAnnotationException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        ImportErrorCase2Spec spec = new ImportErrorCase2Spec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);   //Todo: This gives ImportAndProcessAnnotationException
        fluentTQLUserInterface.getFluentTQLSpecification();
    }
}
