package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the given field is not public and therefore failed to configure it.
 *
 * @author Ranjith Krishnamurthy
 */
public class FieldNotPublicException extends FluentTQLException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
