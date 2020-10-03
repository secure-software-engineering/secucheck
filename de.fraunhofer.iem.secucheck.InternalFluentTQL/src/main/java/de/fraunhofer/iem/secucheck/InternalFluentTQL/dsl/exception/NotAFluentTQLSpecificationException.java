package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that the given class name is not a FluentTQL specification.
 *
 * @author Ranjith Krishnamurthy
 */
public class NotAFluentTQLSpecificationException extends FluentTQLException {
    /**
     * Constructs the NotAFluentTQLSpecificationException with the corresponding error message.
     *
     * @param className Class Name
     */
    public NotAFluentTQLSpecificationException(String className) {
        super("\n\"" + className + "\" is not a FluentTQL Specification. " +
                "Please use the FluentTQLSpecificationClass annotation to annotate a class as FluentTQl Specification.");
    }
}
