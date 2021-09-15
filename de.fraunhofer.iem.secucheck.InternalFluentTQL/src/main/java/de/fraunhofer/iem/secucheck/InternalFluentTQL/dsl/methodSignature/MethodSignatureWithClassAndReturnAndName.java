package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.ArrayList;
import java.util.List;

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

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(String methodParameter) {
        String originalMethodParameter = methodParameter;

        if (isApplyTypeAliases) {
            originalMethodParameter = kotlinTypeAliasChecker.getOriginalTypeName(methodParameter, typeAliases);

            if (originalMethodParameter == null) {
                throw new CyclicTypeAliasException(methodParameter);
            }
        }

        return new MethodSignatureWithClassAndReturnAndNameAndParam(
                originalMethodParameter,
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }

    public MethodSignatureWithClassAndReturnAndNameAndParam parameter(List<String> methodParameters) {
        if (isApplyTypeAliases) {
            List<String> originalMethodParameters = new ArrayList<>();

            for (String elem : methodParameters) {
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
