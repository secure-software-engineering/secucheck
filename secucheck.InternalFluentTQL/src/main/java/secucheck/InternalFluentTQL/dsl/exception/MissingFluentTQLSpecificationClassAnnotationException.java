package secucheck.InternalFluentTQL.dsl.exception;

/**
 * This Exception indicates that the given class implements FluentTQLUserInterface, but FluentTQLSpecificationClass annotation is not used.
 *
 */
public class MissingFluentTQLSpecificationClassAnnotationException extends FluentTQLException {
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
