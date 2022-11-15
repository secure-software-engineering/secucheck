package de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to configure the method, that there is a taint flow information going into the method through the given parameterID.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface InFlowParam {
    /**
     * Parameter ID, Note: First parameter id is 0.
     *
     * @return Parameter ID.
     */
    public int[] parameterID();
}
