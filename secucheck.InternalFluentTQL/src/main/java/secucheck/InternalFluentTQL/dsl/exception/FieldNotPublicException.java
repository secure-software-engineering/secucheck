package secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the given field is not public and therefore failed to configure it.
 *
 */
public class FieldNotPublicException extends FluentTQLException {
    /**
     * Constructs the FieldNotPublicException with the given message.
     *
     * @param fieldName Field name.
     */
    public FieldNotPublicException(String fieldName, String className) {
        super("\"" + fieldName + "\" in \"" + className + "\" class is not public, failed to configure the field. " +
                "Please make the field public.");
    }
}
