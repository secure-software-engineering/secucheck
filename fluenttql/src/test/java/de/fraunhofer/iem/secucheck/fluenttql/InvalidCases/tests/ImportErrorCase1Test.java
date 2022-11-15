package de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.tests;

import de.fraunhofer.iem.secucheck.fluenttql.InvalidCases.specifications.ImportErrorCase1Spec;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.ProcessAnnotatedClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.FluentTQLException;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.ImportAndProcessAnnotationException;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface.FluentTQLUserInterface;
import org.junit.Test;

public class ImportErrorCase1Test {
    @Test(expected = ImportAndProcessAnnotationException.class)
    public void NullInThroughMethodCase() throws FluentTQLException {
        ImportErrorCase1Spec spec = new ImportErrorCase1Spec();
        ProcessAnnotatedClass processAnnotatedClass = new ProcessAnnotatedClass();
        FluentTQLUserInterface fluentTQLUserInterface = (FluentTQLUserInterface) processAnnotatedClass.processFluentTQLAnnotation(spec);   //Todo: This gives ImportAndProcessAnnotationException
        fluentTQLUserInterface.getFluentTQLSpecification();
    }
}
