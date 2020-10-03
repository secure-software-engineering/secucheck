package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to configure the method, that there is a taint flow information coming out of the method through the given parameterID.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface OutFlowParam {
    public int[] parameterID();
}
