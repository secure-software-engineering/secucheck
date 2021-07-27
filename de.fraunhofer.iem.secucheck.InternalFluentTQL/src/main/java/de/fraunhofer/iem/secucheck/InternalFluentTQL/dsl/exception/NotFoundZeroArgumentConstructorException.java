package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that, there is no constructor with 0 number of arguments, therefore failed to instantiate object and process annotation.
 *
 * @author Ranjith Krishnamurthy
 */
public class NotFoundZeroArgumentConstructorException extends FluentTQLException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs NotFoundZeroArgumentConstructorException with corresponding message.
     *
     * @param className Class Name.
     */
    public NotFoundZeroArgumentConstructorException(String className) {
        super("\"" + className + "\" class does not have a constructor with 0 argument. Please create a constructor with 0 argument. " +
                "Note: FluentTQL can not be interface.");
    }
}
