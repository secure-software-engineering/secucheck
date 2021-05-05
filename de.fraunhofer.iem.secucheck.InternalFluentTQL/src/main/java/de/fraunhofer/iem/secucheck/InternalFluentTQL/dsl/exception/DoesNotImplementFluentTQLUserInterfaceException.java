package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the given class does not implement FluentTQLUserInterface, therefore FluentTQLSpecification can not
 * be retrieved.
 *
 * @author Ranjith Krishnamurthy
 */
public class DoesNotImplementFluentTQLUserInterfaceException extends FluentTQLException {
    /**
     * Constructs the DoesNotImplementFluentTQLUserInterfaceException with the corresponding error message.
     *
     * @param className Class Name
     */
    public DoesNotImplementFluentTQLUserInterfaceException(String className) {
        super("\n\"" + className + "\" does not implement FluentTQLUserInterface. " +
                "Please implement FluentTQLUserInterface to make this class a FluentTQl Specification.");
    }
}
