package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with class name operator.
 * <p>
 * Example: MethodSignatureConfigurator().atClass("...")
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureWithClass {

    private final MethodSignatureImpl methodSignature;

    public MethodSignatureWithClass(String methodClass, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.setFullyQualifiedClassName(methodClass);
    }

    public MethodSignatureWithClassAndReturn returns(String returnType) {
        return new MethodSignatureWithClassAndReturn(returnType, methodSignature);
    }

}
