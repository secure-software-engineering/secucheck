package de.fraunhofer.iem.secucheck.fluenttql.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.MethodSignature;

import java.util.List;

/**
 * This class represents that it contains {@link MethodSignature} with returns, name, and accepts operators.
 * Example: MethodSignatureBuilder().returns("...").named("...").accepts("...")
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureWithClassAndReturnAndNameAndParam {
    private final MethodSignatureImpl methodSignature;

    public MethodSignatureWithClassAndReturnAndNameAndParam(String methodParameter, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.addParameterType(methodParameter);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam(List<String> methodParameters, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.addParametersType(methodParameters);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String methodParam) {
        this.methodSignature.addParameterType(methodParam);

        return this;
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
