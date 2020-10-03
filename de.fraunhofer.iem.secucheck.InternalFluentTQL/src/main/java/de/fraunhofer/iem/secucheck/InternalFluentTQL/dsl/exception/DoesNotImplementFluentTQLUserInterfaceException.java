package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

public class DoesNotImplementFluentTQLUserInterfaceException extends FluentTQLException {
    public DoesNotImplementFluentTQLUserInterfaceException(String className) {
        super("\n\"" + className + "\" does not implement FluentTQLUserInterface. " +
                "Please implement FluentTQLUserInterface to make this class a FluentTQl Specification.");
    }
}
