package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.annotations.ImportAndProcessAnnotation;

/**
 * This exception indicates that there is an error while processing the field annotated with {@link ImportAndProcessAnnotation}.
 *
 * @author Ranjith Krishnamurthy
 */
public class ImportAndProcessAnnotationException extends FluentTQLException {
    /**
     * Constructs the ImportAndProcessAnnotationException with the corresponding error message.
     *
     * @param classNameToImport Class name that should be import and process
     * @param classThatImports  Class that is trying to import
     * @param reason            Reason for error
     */
    public ImportAndProcessAnnotationException(String classNameToImport, String classThatImports, String reason) {
        super("Failed to import and process \"" + classNameToImport + "\" in \"" + classThatImports + "\". \n" +
                "Reason: " + reason);
    }
}
