package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinUtility.KotlinDataTypeTransformer;

/**
 * This class represents that it contains {@link MethodSignature} with class and return operator.
 * <p>
 * Example: MethodSignatureBuilder().atClass("...").returns("...")
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureWithClassAndReturn {
    private final MethodSignatureImpl methodSignature;

    public MethodSignatureWithClassAndReturn(String returnType, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.setReturnType(KotlinDataTypeTransformer.transform(returnType));
    }

    public MethodSignatureWithClassAndReturnAndName named(String methodName) {
        return new MethodSignatureWithClassAndReturnAndName(methodName, methodSignature);
    }
}
