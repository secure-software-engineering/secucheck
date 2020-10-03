package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to configure the method, that there is a taint flow information going into the method through this object.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@Documented
public @interface InFlowThisObject {
}
