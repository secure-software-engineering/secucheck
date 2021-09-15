package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns and name operators.
 * Example: MethodSignatureConfigurator().returns("...").named("...")
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureWithClassAndReturnAndName {
    private final MethodSignatureImpl methodSignature;

    public MethodSignatureWithClassAndReturnAndName(String methodName, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.setMethodName(methodName);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String methodParam) {
        return new MethodSignatureWithClassAndReturnAndNameAndParam(methodParam, methodSignature);
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
