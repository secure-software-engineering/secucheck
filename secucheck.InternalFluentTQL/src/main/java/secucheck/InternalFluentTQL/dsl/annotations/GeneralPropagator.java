package secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to indicate that the annotated field is an general propagator.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface GeneralPropagator {
}
