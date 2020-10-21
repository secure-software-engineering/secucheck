package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This class indicates that there is a cyclic import annotations and could not process.
 *
 * @author Ranjith Krishnamurthy
 */
public class CyclicImportException extends FluentTQLException {
    /**
     * Constructs the CyclicImportException with the corresponding error message.
     *
     * @param chain Chain that caused Cyclic import
     */
    public CyclicImportException(String chain) {
        super("\nThere is a cyclic import statements. Please remove the cyclic imports.\n" + chain);
    }
}
