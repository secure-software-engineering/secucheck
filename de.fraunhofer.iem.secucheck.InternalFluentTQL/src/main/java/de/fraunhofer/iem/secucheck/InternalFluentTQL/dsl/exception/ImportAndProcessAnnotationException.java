package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

public class ImportAndProcessAnnotationException extends FluentTQLException {
    public ImportAndProcessAnnotationException(String className) {
        super("\nFailed to import and process \"" + className + "\". " +
                "To import and process, class should have either FluentTQLSpecificationClass or FluentTQLRepositoryClass annotation.");
    }
}
