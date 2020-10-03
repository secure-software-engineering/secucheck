package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to indicate that the annotated class is an FluentTQL Repository that contains the Method declaration
 * that can be used by the other FluentTQLSpecifications.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FluentTQLRepositoryClass {
}
