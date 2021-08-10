package secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to configure the method, that there is a taint flow information coming out of the method through this object.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface OutFlowThisObject {
}
