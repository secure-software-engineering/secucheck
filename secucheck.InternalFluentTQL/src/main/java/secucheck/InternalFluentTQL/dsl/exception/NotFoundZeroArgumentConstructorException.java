package secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that, there is no constructor with 0 number of arguments, therefore failed to instantiate object and process annotation.
 *
 */
public class NotFoundZeroArgumentConstructorException extends FluentTQLException {
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
