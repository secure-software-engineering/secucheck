package secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * This annotation is used to import and process only static field of the given list of classes.
 * <p>
 * Given classes must be FluentTQL related class otherwise, NotAFluentTQLRelatedClassException will be thrown at the runtime.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ImportAndProcessOnlyStaticFields {
    /**
     * List of classes, whose static field must be processed.
     *
     * @return List of classes
     */
    public Class<?>[] classList();
}
