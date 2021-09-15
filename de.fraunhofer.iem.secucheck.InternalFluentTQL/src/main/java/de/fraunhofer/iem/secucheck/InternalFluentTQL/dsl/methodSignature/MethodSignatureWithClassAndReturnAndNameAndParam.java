package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.ArrayList;
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

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    protected MethodSignatureWithClassAndReturnAndNameAndParam(
            String parameterType,
            MethodSignatureImpl methodSignature,
            TypeAliases typeAliases,
            KotlinTypeAliasChecker kotlinTypeAliasChecker,
            boolean isApplyTypeAliases) {
        this.methodSignature = methodSignature;
        this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(parameterType));

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = kotlinTypeAliasChecker;
        this.isApplyTypeAliases = isApplyTypeAliases;
    }

    protected MethodSignatureWithClassAndReturnAndNameAndParam(
            List<String> parametersType,
            MethodSignatureImpl methodSignature,
            TypeAliases typeAliases,
            KotlinTypeAliasChecker kotlinTypeAliasChecker,
            boolean isApplyTypeAliases) {
        this.methodSignature = methodSignature;

        for (String elem : parametersType) {
            this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(elem));
        }

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = kotlinTypeAliasChecker;
        this.isApplyTypeAliases = isApplyTypeAliases;
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String parameterType) {
        String originalParameterType = parameterType;

        if (isApplyTypeAliases) {
            originalParameterType = kotlinTypeAliasChecker.getOriginalTypeName(parameterType, typeAliases);

            if (originalParameterType == null) {
                throw new CyclicTypeAliasException(parameterType);
            }
        }

        this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(originalParameterType));

        return this;
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(List<String> parametersType) {
        List<String> originalParametersType = new ArrayList<>();

        if (isApplyTypeAliases) {
            for (String elem : parametersType) {
                String originalMethodParameter;

                originalMethodParameter = kotlinTypeAliasChecker.getOriginalTypeName(elem, typeAliases);

                if (originalMethodParameter == null) {
                    throw new CyclicTypeAliasException(elem);
                }

                originalParametersType.add(originalMethodParameter);
            }
        } else {
            originalParametersType.addAll(parametersType);
        }

        for (String elem : originalParametersType) {
            this.methodSignature.addParameterType(KotlinDataTypeTransformer.transform(elem));
        }

        return this;
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
