package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations.FluentTQLSpecificationClass;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.SpecificationInterface.FluentTQLUserInterface;

/**
 * This exception indicates that the given class implements {@link FluentTQLUserInterface}, but {@link FluentTQLSpecificationClass} annotation is not used.
 *
 * @author Ranjith Krishnamurthy
 */
public class MissingFluentTQLSpecificationClassAnnotationException extends FluentTQLException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs the MissingFluentTQLSpecificationClassAnnotationException with the corresponding message.
     *
     * @param className Class Name.
     */
    public MissingFluentTQLSpecificationClassAnnotationException(String className) {
        super("\n\"" + className + "\" implements FluentTQLUserInterface but does not have FluentTQLSpecificationClass annotation. " +
                "Please use the FluentTQLSpecificationClass annotation to annotate a class as FluentTQl Specification.");
    }
}
