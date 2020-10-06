package de.fraunhofer.iem.secucheck.FluentTQLMagpieBridge.FluentTQLCompiler;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class ErrorModel {
    private int id;
    private String fullyQualifiedClassName;
    private String errorMessage;
    private String stackTrace;

    public ErrorModel(int id, String fullyQualifiedClassName, String errorMessage) {
        this.id = id;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.errorMessage = errorMessage;
        this.stackTrace = "Not Available";
    }

    public ErrorModel(int id, String fullyQualifiedClassName, String errorMessage, String stackTrace) {
        this.id = id;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "\n\n" +
                id + ". " + fullyQualifiedClassName + "\n" +
                StringUtils.repeat('*', fullyQualifiedClassName.length() + 8) + "\n\n" +
                "Error Message: \n" + StringUtils.repeat('-', "Error Message: ".length() + 3) + "\n\n" +
                errorMessage + "\n\n" +
                "Stack Trace: \n" + StringUtils.repeat('-', "Stack Trace: ".length() + 3) + "\n\n" +
                stackTrace + "\n\n";
    }
}
