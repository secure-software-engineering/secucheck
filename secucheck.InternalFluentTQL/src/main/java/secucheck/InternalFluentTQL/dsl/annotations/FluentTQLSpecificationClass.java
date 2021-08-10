package secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to indicate that the annotated class is an FluentTQL specification. Note that, if the class is annotated with
 * this annotation, then FluentTQLUserInterface must be implemented by the class, otherwise DoesNotImplementFluentTQLUserInterfaceException
 * will be thrown at the runtime.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FluentTQLSpecificationClass {
}
