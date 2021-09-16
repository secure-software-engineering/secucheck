package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException;

/**
 * This exception indicates that the invalid qualified this is used while configuring the Method
 *
 * @author Ranjith Krishnamurthy
 */
public class InvalidQualifiedThisException extends FluentTQLRuntimeException {
    /**
     * Constructs the InvalidQualifiedThisException with the given message.
     *
     * @param message Error message.
     */
    public InvalidQualifiedThisException(String message) {
        super("Invalid Qualified This :\n" + message);
    }
}
