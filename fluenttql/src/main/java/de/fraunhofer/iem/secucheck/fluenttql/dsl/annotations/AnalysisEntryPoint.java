package de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations;

import java.lang.annotation.*;

/**
 * This annotation indicates that the annotated method is a entry point for the analysis.
 *
 * @author Ranjith Krishnamurthy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AnalysisEntryPoint {
}
