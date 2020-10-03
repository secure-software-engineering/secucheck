package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

public class NotAFluentTQLSpecificationException extends FluentTQLException {
    public NotAFluentTQLSpecificationException(String className) {
        super("\n\"" + className + "\" is not a FluentTQL Specification. " +
                "Please use the FluentTQLSpecificationClass annotation to annotate a class as FluentTQl Specification.");
    }
}
