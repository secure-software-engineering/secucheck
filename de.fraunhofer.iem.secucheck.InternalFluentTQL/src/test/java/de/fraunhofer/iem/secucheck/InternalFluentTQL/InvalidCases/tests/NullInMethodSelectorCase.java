package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.NullInMethodSelectorCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

public class NullInMethodSelectorCase {
    @Test(expected = NullPointerException.class)
    public void test1() throws FluentTQLException {
        NullInMethodSelectorCaseSpec spec = new NullInMethodSelectorCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLAnnotation(spec);
    }
}
