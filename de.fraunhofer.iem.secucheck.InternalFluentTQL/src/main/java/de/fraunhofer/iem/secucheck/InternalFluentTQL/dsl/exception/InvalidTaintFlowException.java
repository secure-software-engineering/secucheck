package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception;

import java.util.List;

/**
 * This exception indicates that the fluentTQL specification is broken and some of the methods are not configured.
 *
 * @author Ranjith Krishnamurthy
 */
public class InvalidTaintFlowException extends FluentTQLException {
    /**
     * Constructs the InvalidTaintFlowException with the corresponding error message.
     *
     * @param methodSignatureList List of method signature that is not configured.
     */
    public InvalidTaintFlowException(List<String> methodSignatureList) {
        super(
                "FluentTQL specification(s) is/are broken. \nBelow method(s) are not configured and has invalid taint flow. Please " +
                        "use FluentTQL annotation to configure the methods. \n" +
                        methodSignatureList.toString()
                                .replaceAll("\\[", "* ")
                                .replaceAll("]", "")
                                .replaceAll(", ", "\n* ")
        );
    }
}
