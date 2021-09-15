package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinUtility.KotlinDataTypeTransformer;

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

    public MethodSignatureWithClassAndReturnAndNameAndParam(String parameterType, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;
        this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(parameterType));
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam(List<String> parametersType, MethodSignatureImpl methodSignature) {
        this.methodSignature = methodSignature;

        for (String elem : parametersType) {
            this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(elem));
        }
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String parameterType) {
        this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(parameterType));

        return this;
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(List<String> parametersType) {
        for (String elem : parametersType) {
            this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(elem));
        }

        return this;
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
