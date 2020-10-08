package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.ReturnsEmptySpecificationCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.InvalidFluentTQLSpecificationException;
import org.junit.Test;

public class ReturnsEmptySpecificationCase {
    @Test(expected = InvalidFluentTQLSpecificationException.class)
    public void test1() throws FluentTQLException {
        ReturnsEmptySpecificationCaseSpec spec = new ReturnsEmptySpecificationCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLSpecificationClassAnnotation(spec);
    }
}
