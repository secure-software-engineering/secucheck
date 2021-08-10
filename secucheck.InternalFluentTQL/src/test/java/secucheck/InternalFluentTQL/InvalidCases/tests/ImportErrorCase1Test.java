package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.ImportErrorCase1Spec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.dsl.exception.ImportAndProcessAnnotationException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class ImportErrorCase1Test {
    @Test(expected = ImportAndProcessAnnotationException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        ImportErrorCase1Spec spec = new ImportErrorCase1Spec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);   //Todo: This gives ImportAndProcessAnnotationException
        fluentTQLUserInterface.getFluentTQLSpecification();
    }
}
