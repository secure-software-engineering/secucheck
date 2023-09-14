package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.ImportErrorCase2Spec;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.ImportAndProcessAnnotationException;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class ImportErrorCase2Test {
    @Test(expected = ImportAndProcessAnnotationException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        ImportErrorCase2Spec spec = new ImportErrorCase2Spec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);   //Todo: This gives ImportAndProcessAnnotationException
        fluentTQLUserInterface.getFluentTQLSpecification();
    }
}
