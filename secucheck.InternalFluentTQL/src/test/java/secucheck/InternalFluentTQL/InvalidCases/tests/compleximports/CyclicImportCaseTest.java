package secucheck.InternalFluentTQL.InvalidCases.tests.compleximports;

import secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports.CyclicImportCaseSpec;
import secucheck.InternalFluentTQL.InvalidCases.specifications.compleximports.FirstRepository;
import secucheck.InternalFluentTQL.dsl.ProcessAnnotatedClass;
import secucheck.InternalFluentTQL.dsl.exception.CyclicImportException;
import secucheck.InternalFluentTQL.dsl.exception.FluentTQLException;
import org.junit.Test;

public class CyclicImportCaseTest {
    @Test(expected = CyclicImportException.class)
    public void test1() throws FluentTQLException {
        new FirstRepository();
        CyclicImportCaseSpec spec = new CyclicImportCaseSpec();
        new ProcessAnnotatedClass().processFluentTQLSpecificationClassAnnotation(spec);
    }
}
