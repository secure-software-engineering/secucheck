package secucheck.InternalFluentTQL.dsl.annotations;

import java.lang.annotation.*;

/**
 * This annotation indicates that the annotated method is a entry point for the analysis.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AnalysisEntryPoint {
}
