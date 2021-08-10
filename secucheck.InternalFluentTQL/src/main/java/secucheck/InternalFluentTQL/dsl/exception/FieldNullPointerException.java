package secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the Field is null and can not be configure the taint flow from the provided annotation.
 *
 */
public class FieldNullPointerException extends FluentTQLException {
    /**
     * Constructs the FieldNullPointerException with the corresponding error message.
     *
     * @param fieldName Field Name
     * @param className Field's Class Name
     */
    public FieldNullPointerException(String fieldName, String className) {
        super("\nField \"" + fieldName + "\" in \"" + className + "\" class is null and not initialized. " +
                "Please initialize while declaring the field.");
    }
}
