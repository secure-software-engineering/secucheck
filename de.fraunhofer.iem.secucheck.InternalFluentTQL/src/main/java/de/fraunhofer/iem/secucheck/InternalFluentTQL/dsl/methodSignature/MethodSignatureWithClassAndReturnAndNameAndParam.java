package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            this.methodSignature.addParameterType(
                    KotlinDataTypeTransformer.transform(
                            KotlinDataTypeTransformer.replaceFunctionType(elem)
                    ));
        }

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = kotlinTypeAliasChecker;
        this.isApplyTypeAliases = isApplyTypeAliases;
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String... methodParameter) {
        Objects.requireNonNull(methodParameter, "given methodParameter to parameter() is null");

        for (String elem : methodParameter) {
            Objects.requireNonNull(elem, "one of the elements of methodParameter in parameter() is null");

            String temp = elem;

            if (isApplyTypeAliases) {
                temp = kotlinTypeAliasChecker.getOriginalTypeName(elem, typeAliases);
            }

            if (temp == null) {
                throw new CyclicTypeAliasException(elem);
            }

            this.methodSignature.addParameterType(
                    KotlinDataTypeTransformer.transform(
                            KotlinDataTypeTransformer.replaceFunctionType(temp)
                    ));
        }

        return this;
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(List<String> parametersType) {
        Objects.requireNonNull(parametersType, "given parametersType to parameter() is null");

        List<String> originalParametersType = new ArrayList<>();

        if (isApplyTypeAliases) {
            for (String elem : parametersType) {
                Objects.requireNonNull(elem, "one of the elements of parametersType in parameter() is null");

                String originalMethodParameter;

                originalMethodParameter = kotlinTypeAliasChecker.getOriginalTypeName(elem, typeAliases);

                if (originalMethodParameter == null) {
                    throw new CyclicTypeAliasException(elem);
                }

                originalParametersType.add(originalMethodParameter);
            }
        } else {
            if (parametersType.contains(null))
                throw new NullPointerException("one of elements of parametersType in parameter() is null");

            originalParametersType.addAll(parametersType);
        }

        for (String elem : originalParametersType) {
            this.methodSignature.addParameterType(
                    KotlinDataTypeTransformer.transform(
                            KotlinDataTypeTransformer.replaceFunctionType(elem)
                    ));
        }

        return this;
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
