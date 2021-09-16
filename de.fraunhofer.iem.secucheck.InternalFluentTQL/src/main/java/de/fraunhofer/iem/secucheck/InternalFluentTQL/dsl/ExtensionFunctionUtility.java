package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.InvalidQualifiedThisException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Parameter;

/**
 * Utility for the Extension function
 *
 * @author Ranjith Krishnamurthy
 */
class ExtensionFunctionUtility {
    private static Object getQualifiedThis(QualifiedThis qualifiedThis, MethodImpl method) {
        if (method.getMethodSignature().isExtensionFunction()) {
            if (qualifiedThis == QualifiedThis.EXTENSION_RECEIVER) {
                return new ParameterImpl(0);
            } else {
                if (method.getMethodSignature().isTopLevelMember()) {
                    throw new InvalidQualifiedThisException(method.getMethodSignature() + " is a top level extension function" +
                            " and the DISPATCH_RECEIVER is used as qualified this, which is invalid.");
                }

                return new ThisObjectImpl();
            }
        } else {
            if (qualifiedThis == QualifiedThis.EXTENSION_RECEIVER) {
                throw new InvalidQualifiedThisException(method.getMethodSignature() + " is a not an extension function" +
                        " and the EXTENSION_RECEIVER is used as qualified this, which is invalid.");
            }

            return new ThisObjectImpl();
        }
    }

    /**
     * Returns the correct Output for this Object for Extension function
     *
     * @param method Method
     * @return Correct Output
     */
    public static Output getQualifiedThisOutPut(QualifiedThis qualifiedThis, MethodImpl method) {
        return (Output) getQualifiedThis(qualifiedThis, method);
    }

    /**
     * Returns the correct Input for this Object for Extension function
     *
     * @param method Method
     * @return Correct Input
     */
    public static Input getQualifiedThisInput(QualifiedThis qualifiedThis, MethodImpl method) {
        return (Input) getQualifiedThis(qualifiedThis, method);
    }

    /**
     * Returns the valid parameter ID if a function is a extension function
     *
     * @param param               Current parameter ID
     * @param isExtensionFunction is the function extension function
     * @return Valid parameter ID based on the isExtensionFunction
     */
    public static Parameter getCorrectParameterID(int param, boolean isExtensionFunction) {
        if (isExtensionFunction)
            return new ParameterImpl(param + 1);

        return new ParameterImpl(param);
    }

    private static Object getDefaultThisObjectForExtension(boolean isExtensionFunction) {
        if (isExtensionFunction)
            return new ParameterImpl(0);

        return new ThisObjectImpl();
    }

    /**
     * Returns the default Input for this Object for Extension function
     *
     * @param isExtensionFunction is the function extension function
     * @return Default Input
     */
    public static Input getDefaultInputThisObjectForExtension(boolean isExtensionFunction) {
        return (Input) getDefaultThisObjectForExtension(isExtensionFunction);
    }

    /**
     * Returns the default Output for this Object for Extension function
     *
     * @param isExtensionFunction is the function extension function
     * @return Default Output
     */
    public static Output getDefaultOutputThisObjectForExtension(boolean isExtensionFunction) {
        return (Output) getDefaultThisObjectForExtension(isExtensionFunction);
    }
}
