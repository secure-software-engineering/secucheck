package de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.tests.compleximports;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports.CyclicImportCaseSpec;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports.FirstRepository;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.CyclicImportException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

public class CyclicImportCaseTest {
    @Test(expected = CyclicImportException.class)
    public void test1() throws FluentTQLException {
        new FirstRepository();
        CyclicImportCaseSpec spec = new CyclicImportCaseSpec();
        new ProcessAnnotatedClass().processFluentTQLSpecificationClassAnnotation(spec);
    }
}
