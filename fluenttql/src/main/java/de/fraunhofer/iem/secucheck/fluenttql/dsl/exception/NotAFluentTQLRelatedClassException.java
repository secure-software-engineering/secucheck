package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLRepositoryClass;
import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.FluentTQLSpecificationClass;

/**
 * This exception indicates that the class does not have {@link FluentTQLSpecificationClass} or {@link FluentTQLRepositoryClass} annotation.
 *
 * @author Ranjith Krishnamurthy
 */
public class NotAFluentTQLRelatedClassException extends FluentTQLException {
    /**
     * Constructs the NotAFluentTQLRelatedClassException with the corresponding message.
     *
     * @param className Class Name.
     */
    public NotAFluentTQLRelatedClassException(String className) {
        super("\n\"" + className + "\" is not a FluentTQL related class. " +
                "Please use one of the [FluentTQLSpecificationClass, FluentTQLRepositoryClass] annotation to annotate a class as FluentTQl related class.");
    }
}
