package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class represents that it contains {@link MethodSignature} with returns and name operators.
 * Example: MethodSignatureBuilder().returns("...").named("...")
 *
 * @author Ranjith Krishnamurthy
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturnAndName {
    private final MethodSignatureImpl methodSignature;

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    protected MethodSignatureWithClassAndReturnAndName(
            String methodName,
            MethodSignatureImpl methodSignature,
            TypeAliases typeAliases,
            KotlinTypeAliasChecker kotlinTypeAliasChecker,
            boolean isApplyTypeAliases) {
        this.methodSignature = methodSignature;
        this.methodSignature.setMethodName(methodName);

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = kotlinTypeAliasChecker;
        this.isApplyTypeAliases = isApplyTypeAliases;
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String... methodParameter) {
        Objects.requireNonNull(methodParameter, "given methodParameter to parameter() is null");

        List<String> originalMethodParameter = new ArrayList<>();

        if (isApplyTypeAliases) {
            for (String elem : methodParameter) {
                Objects.requireNonNull(elem, "one of the elements of methodParameter in parameter() is null");

                String temp = kotlinTypeAliasChecker.getOriginalTypeName(elem, typeAliases);

                if (temp == null) {
                    throw new CyclicTypeAliasException(elem);
                }

                originalMethodParameter.add(temp);
            }
        } else {
            if (Arrays.asList(methodParameter).contains(null))
                throw new NullPointerException("one of the elements of methodParameter in parameter() is null");

            originalMethodParameter.addAll(Arrays.asList(methodParameter));
        }

        return new MethodSignatureWithClassAndReturnAndNameAndParam(
                originalMethodParameter,
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(List<String> methodParameters) {
        Objects.requireNonNull(methodParameters, "given methodParameters to parameter() is null");

        if (isApplyTypeAliases) {
            List<String> originalMethodParameters = new ArrayList<>();

            for (String elem : methodParameters) {
                Objects.requireNonNull(elem, "one of the elements of methodParameters in parameter() is null");

                String originalMethodParameter;

                originalMethodParameter = kotlinTypeAliasChecker.getOriginalTypeName(elem, typeAliases);

                if (originalMethodParameter == null) {
                    throw new CyclicTypeAliasException(elem);
                }

                originalMethodParameters.add(originalMethodParameter);
            }

            return new MethodSignatureWithClassAndReturnAndNameAndParam(
                    originalMethodParameters,
                    methodSignature,
                    typeAliases,
                    kotlinTypeAliasChecker,
                    isApplyTypeAliases);
        }

        if (methodParameters.contains(null))
            throw new NullPointerException("one of elements of methodParameters in parameter() is null");

        return new MethodSignatureWithClassAndReturnAndNameAndParam(
                methodParameters,
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }

    public MethodSignature configure() {
        return methodSignature;
    }
}
