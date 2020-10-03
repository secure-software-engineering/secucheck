package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

public class FieldNullPointerException extends FluentTQLException {
    public FieldNullPointerException(String fieldName, String className) {
        super("\nField \"" + fieldName + "\" in \"" + className + "\" class is null and not initialized. " +
                "Please initialize while declaring the field.");
    }
}
