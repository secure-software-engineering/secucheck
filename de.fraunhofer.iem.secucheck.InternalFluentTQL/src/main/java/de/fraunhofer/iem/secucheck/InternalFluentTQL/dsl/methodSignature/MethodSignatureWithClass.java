package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinUtility.KotlinDataTypeTransformer;

/**
 * This class represents that it contains {@link MethodSignature} with class name operator.
 * <p>
 * Example: MethodSignatureBuilder().atClass("...")
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureWithClass {

    private final MethodSignatureImpl methodSignature;

    public MethodSignatureWithClass(String fullyQualifiedClassName, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.setFullyQualifiedClassName(KotlinDataTypeTransformer.transform(fullyQualifiedClassName));
    }

    public MethodSignatureWithClassAndReturn returns(String returnType) {
        return new MethodSignatureWithClassAndReturn(returnType, methodSignature);
    }

}
