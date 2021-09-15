package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns, name, and accepts operators.
 * Example: MethodSignatureConfigurator().returns("...").named("...").accepts("...")
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureWithClassAndReturnAndNameAndParam {
    private final MethodSignatureImpl methodSignature;

    public MethodSignatureWithClassAndReturnAndNameAndParam(String methodParam, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.addParameterType(methodParam);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String methodParam) {
        this.methodSignature.addParameterType(methodParam);

        return this;
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
