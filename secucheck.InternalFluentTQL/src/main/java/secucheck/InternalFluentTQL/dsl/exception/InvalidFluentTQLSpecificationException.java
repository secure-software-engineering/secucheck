package secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the fluentTQL specification is broken and some of the methods are not configured or empty specification list is returned.
 *
 */
public class InvalidFluentTQLSpecificationException extends FluentTQLException {
    /**
     * Constructs the InvalidFluentTQLSpecificationException with the corresponding error message.
     *
     * @param reason Reason.
     */
    public InvalidFluentTQLSpecificationException(String reason) {
        super("FluentTQL specification(s) is/are broken. \n" + reason);
    }
}
