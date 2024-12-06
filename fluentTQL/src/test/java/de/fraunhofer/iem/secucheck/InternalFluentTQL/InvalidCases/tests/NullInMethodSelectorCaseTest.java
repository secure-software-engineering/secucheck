package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.NullInMethodSelectorCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

public class NullInMethodSelectorCaseTest {
    @Test(expected = NullPointerException.class)
    public void NullInMethodSelectorCase() throws FluentTQLException {
        NullInMethodSelectorCaseSpec spec = new NullInMethodSelectorCaseSpec();    //Todo: This gives NullPointerException
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        processAnnotatedClass.processFluentTQLAnnotation(spec);
    }
}
