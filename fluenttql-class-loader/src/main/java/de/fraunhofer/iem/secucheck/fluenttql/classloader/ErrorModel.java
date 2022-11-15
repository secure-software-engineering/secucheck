package de.fraunhofer.iem.secucheck.fluenttql.classloader;

import org.apache.commons.lang3.StringUtils;

/**
 * This class represents the error that occurred in a single class.
 *
 * @author Ranjith Krishnamurthy
 */
public class ErrorModel {
    private final int id;
    private String fullyQualifiedClassName;
    private String errorMessage;
    private String stackTrace;

    /**
     * Constructs the ErrorModel
     *
     * @param id                      Unique Identifier
     * @param fullyQualifiedClassName Fully Qualified class name
     * @param errorMessage            Error message
     */
    public ErrorModel(int id, String fullyQualifiedClassName, String errorMessage) {
        this.id = id;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.errorMessage = errorMessage;
        this.stackTrace = "Not Available";
    }

    /**
     * Constructs the ErrorModel
     *
     * @param id                      Unique Identifier
     * @param fullyQualifiedClassName Fully Qualified class name
     * @param errorMessage            Error message
     * @param stackTrace              StackTrace
     */
    public ErrorModel(int id, String fullyQualifiedClassName, String errorMessage, String stackTrace) {
        this.id = id;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
    }

    /**
     * Returns the Fully Qualified Class name.
     *
     * @return Fully Qualified Class name.
     */
    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    /**
     * Sets the Fully Qualified Class name.
     *
     * @param fullyQualifiedClassName Fully Qualified Class name.
     */
    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    /**
     * Returns the error message
     *
     * @return Error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the Error message
     *
     * @param errorMessage Error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns the StackTrace
     *
     * @return StackTrace
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * Sets the StackTrace
     *
     * @param stackTrace StakcTrace
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    /**
     * Returns the Complete ErrorModel in String type.
     *
     * @return ErrorModel in String
     */
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
