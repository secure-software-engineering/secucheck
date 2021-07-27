package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the FluentTQL specification is broken and some of the methods are not configured or empty specification list is returned.
 *
 * @author Ranjith Krishnamurthy
 */
public class InvalidFluentTQLSpecificationException extends FluentTQLException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs the InvalidFluentTQLSpecificationException with the corresponding error message.
     *
     * @param reason Reason.
     */
    public InvalidFluentTQLSpecificationException(String reason) {
        super("FluentTQL specification(s) is/are broken. \n" + reason);
    }
}
