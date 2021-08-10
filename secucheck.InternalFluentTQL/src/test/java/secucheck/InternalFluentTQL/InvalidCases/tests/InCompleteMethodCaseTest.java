package secucheck.InternalFluentTQL.InvalidCases.tests;

import secucheck.InternalFluentTQL.InvalidCases.specifications.InCompleteMethodCaseSpec;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import secucheck.InternalFluentTQL.dsl.exception.IncompleteMethodDeclarationException;
import secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class InCompleteMethodCaseTest {
    @Test(expected = IncompleteMethodDeclarationException.class)
    public void NullInNotThroughMethodCase() throws FluentTQLException {
        InCompleteMethodCaseSpec spec = new InCompleteMethodCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);   //Todo: This gives IncompleteMethodDeclarationException
        fluentTQLUserInterface.getFluentTQLSpecification();
    }
}
