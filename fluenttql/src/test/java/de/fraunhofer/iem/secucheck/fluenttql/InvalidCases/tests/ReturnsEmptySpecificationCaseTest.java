package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.ReturnsEmptySpecificationCaseSpec;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.InvalidFluentTQLSpecificationException;
import org.junit.Test;

public class ReturnsEmptySpecificationCaseTest {
    @Test(expected = InvalidFluentTQLSpecificationException.class)
    public void ReturnsEmptySpecificationCase() throws FluentTQLException {
        ReturnsEmptySpecificationCaseSpec spec = new ReturnsEmptySpecificationCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);  //Todo: This gives InvalidFluentTQLSpecificationException
    }
}
