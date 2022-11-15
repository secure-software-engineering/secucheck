package de.fraunhofer.iem.secucheck.fluenttql.dsl.exception.runTimeException;

/**
 * This is the top level runtime exception of the FluentTQL that catches all the FluentTQL related runtime exception.
 *
 * @author Ranjith Krishnamurthy
 */
public class FluentTQLRuntimeException extends RuntimeException {
    /**
     * Constructs the FluentTQLRuntimeException with the given message.
     *
     * @param message Error message.
     */
    public FluentTQLRuntimeException(String message) {
        super(message);
    }
}