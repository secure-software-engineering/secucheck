package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method;

/**
 * This exception indicates that the field of type {@link Method} is not annotated with any of the FluentTQL configure annotation to configure the taint
 * flow in the method.
 *
 * @author Ranjith Krishnamurthy
 */
public class IncompleteMethodDeclarationException extends FluentTQLException {
    /**
     * Constructs the IncompleteMethodDeclarationException with the corresponding error message.
     *
     * @param fieldName Field Name
     * @param className Class Name
     */
    public IncompleteMethodDeclarationException(String fieldName, String className) {
        super("\nThe field \"" + fieldName + "\" in " + "\"" + className + "\" class does have taint flow configuration annotation. " +
                "Please use one of the [InFlowParam, InFlowThisObject, OutFlowParam, " +
                "OutFlowReturnValue, OutFlowThisObject] annotation to configure the method.");
    }
}
