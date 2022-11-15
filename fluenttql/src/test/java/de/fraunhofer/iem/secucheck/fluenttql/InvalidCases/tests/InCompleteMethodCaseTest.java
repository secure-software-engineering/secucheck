package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.InCompleteMethodCaseSpec;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.IncompleteMethodDeclarationException;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
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
