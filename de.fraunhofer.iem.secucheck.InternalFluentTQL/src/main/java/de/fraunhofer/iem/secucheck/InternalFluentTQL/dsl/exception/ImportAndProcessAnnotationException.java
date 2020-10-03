package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This exception indicates that there is an error while processing the field annotated with ImportAndProcessAnnotation.
 *
 * @author Ranjith Krishnamurthy
 */
public class ImportAndProcessAnnotationException extends FluentTQLException {
    /**
     * Constructs the ImportAndProcessAnnotationException with the corresponding error message.
     *
     * @param className Field's class Name.
     */
    public ImportAndProcessAnnotationException(String className) {
        super("\nFailed to import and process \"" + className + "\". " +
                "To import and process, class should have either FluentTQLSpecificationClass or FluentTQLRepositoryClass annotation.");
    }
}
