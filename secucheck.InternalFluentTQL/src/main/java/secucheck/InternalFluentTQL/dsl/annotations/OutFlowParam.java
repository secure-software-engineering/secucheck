package secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * Annotation to configure the method, that there is a taint flow information coming out of the method through the given parameterID.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface OutFlowParam {
    /**
     * Parameter ID, Note: First parameter id is 0.
     *
     * @return Parameter ID.
     */
    public int[] parameterID();
}
