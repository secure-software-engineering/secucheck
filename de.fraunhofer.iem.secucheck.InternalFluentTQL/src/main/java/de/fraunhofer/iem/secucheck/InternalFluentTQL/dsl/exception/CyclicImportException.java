package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

/**
 * This class indicates that there is a cyclic import annotations and could not process.
 *
 * @author Ranjith Krishnamurthy
 */
public class CyclicImportException extends FluentTQLException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs the CyclicImportException with the corresponding error message.
     *
     * @param chain Chain that caused Cyclic import
     */
    public CyclicImportException(String chain) {
        super("\nThere is a cyclic import annotations. Please remove the cyclic imports.\n" + chain);
    }
}
