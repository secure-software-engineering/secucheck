package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

public class IncompleteMethodDeclarationException extends FluentTQLException{
    public IncompleteMethodDeclarationException(String fieldName, String className) {
        super("\nThe field \"" + fieldName + "\" in " + "\"" + className + "\" class does have taint flow configuration annotation. " +
                "Please use one of the [InFlowParam, InFlowThisObject, OutFlowParam, " +
                "OutFlowReturnValue, OutFlowThisObject] annotation to configure the method.");
    }
}
