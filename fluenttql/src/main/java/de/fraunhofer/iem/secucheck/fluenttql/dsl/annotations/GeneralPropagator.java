package de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to indicate that the annotated field is an general propagator.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface GeneralPropagator {
}
