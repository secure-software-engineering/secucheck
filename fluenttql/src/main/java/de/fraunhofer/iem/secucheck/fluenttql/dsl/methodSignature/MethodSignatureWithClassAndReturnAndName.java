package de.fraunhofer.iem.secucheck.fluenttql.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.MethodSignature;

import java.util.List;

/**
 * This class represents that it contains {@link MethodSignature} with returns and name operators.
 * Example: MethodSignatureBuilder().returns("...").named("...")
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

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String methodParameter) {
        return new MethodSignatureWithClassAndReturnAndNameAndParam(methodParameter, methodSignature);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(List<String> methodParameters) {
        return new MethodSignatureWithClassAndReturnAndNameAndParam(methodParameters, methodSignature);
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
