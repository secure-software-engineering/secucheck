package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.NotAFluentTQLSpecificationCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.NotAFluentTQLSpecificationClassException;
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
