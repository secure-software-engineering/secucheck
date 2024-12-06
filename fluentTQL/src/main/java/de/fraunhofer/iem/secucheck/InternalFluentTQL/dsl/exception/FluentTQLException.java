package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This is the top level exception of the FluentTQL that catches all the FluentTQL related exception.
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLException extends Exception {
    /**
     * Constructs the FluentTQLException with the given message.
     *
     * @param message Error message.
     */
    public FluentTQLException(String message) {
        super(message);
    }
}