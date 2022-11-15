package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;

/**
 * This exception indicates that the class is not a {@link FluentTQLSpecification} class and is trying to get the FluentTQL specifications from that class.
 *
 * @author Ranjith Krishnamurthy
 */
public class NotAFluentTQLSpecificationClassException extends FluentTQLException {
    /**
     * Constructs the NotAFluentTQLSpecificationClassException with the corresponding message.
     *
     * @param className Class Name.
     */
    public NotAFluentTQLSpecificationClassException(String className) {
        super("\n\"" + className + "\" is not a FluentTQL Specification class. " +
                "Please use the FluentTQLSpecificationClass annotation to annotate a class as FluentTQl Specification class.");
    }
}
