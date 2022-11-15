package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.FluentTQLSpecification;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

/**
 * This exception indicates that the given class does not implement {@link FluentTQLUserInterface}, therefore {@link FluentTQLSpecification} can not
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
