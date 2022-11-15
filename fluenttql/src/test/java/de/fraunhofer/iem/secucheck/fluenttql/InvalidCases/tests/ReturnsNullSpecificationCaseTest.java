package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.ReturnsNullSpecificationCaseSpec;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import org.junit.Test;

public class ReturnsNullSpecificationCaseTest {
    @Test(expected = NullPointerException.class)
    public void ReturnsNullSpecificationCase() throws FluentTQLException {
        ReturnsNullSpecificationCaseSpec spec = new ReturnsNullSpecificationCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);   //Todo: This gives NullPointerException
    }
}
