package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.ReturnsNullSpecificationCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

public class ReturnsNullSpecificationCaseTest {
    @Test(expected = NullPointerException.class)
    public void ReturnsNullSpecificationCase() throws FluentTQLException {
        ReturnsNullSpecificationCaseSpec spec = new ReturnsNullSpecificationCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);   //Todo: This gives NullPointerException
    }
}
