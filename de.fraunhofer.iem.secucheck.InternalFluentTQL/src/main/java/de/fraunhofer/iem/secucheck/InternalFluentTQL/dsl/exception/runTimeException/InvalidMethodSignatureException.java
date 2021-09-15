package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException;

/**
 * This exception indicates that the given method signature is not valid.
 *
 * @author Ranjith Krishnamurthy
 */
public class InvalidMethodSignatureException extends FluentTQLRuntimeException {
    /**
     * Constructs the InvalidMethodSignatureException with the given message.
     *
     * @param methodSignature Method signature.
     */
    public InvalidMethodSignatureException(String methodSignature) {
        super("\n\nGiven MethodSignature \"" + methodSignature + "\" is invalid.\nPlease follow the below rules to provide the valid" +
                "signature otherwise use the MethodSignatureBuilder\n" +
                "<fullyQualifiedClassName>: <returnType> <className>(parameterType, parameterType, ...)");
    }
}
