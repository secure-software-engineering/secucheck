package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.tests.compleximports;

import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.compleximports.CyclicImportCaseSpec;
import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.compleximports.FirstRepository;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.CyclicImportException;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import org.junit.Test;

public class CyclicImportCaseTest {
    @Test(expected = CyclicImportException.class)
    public void test1() throws FluentTQLException {
        new FirstRepository();
        CyclicImportCaseSpec spec = new CyclicImportCaseSpec();
        new ProcessAnnotatedClass().processFluentTQLSpecificationClassAnnotation(spec);
    }
}
