package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * This annotation is used to process the FluentTQL annotation of the annotated field's class. Field must not be null. Therefore,
 * the annotated field must be initialized during the declaration of that field.
 * <p>
 * Annotated field must be FluentTQL related class otherwise, NotAFluentTQLSpecificationException will be thrown at the runtime.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ImportAndProcessAnnotation {
}
