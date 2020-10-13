package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.NullInReportMethodCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class NullInReportMethodCaseTest {
    @Test(expected = NullPointerException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        NullInReportMethodCaseSpec spec = new NullInReportMethodCaseSpec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);
        fluentTQLUserInterface.getFluentTQLSpecification();   //Todo: This gives NullPointerException
    }
}
