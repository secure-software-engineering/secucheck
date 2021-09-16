package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.Objects;

/**
 * This class represents that the {@link MethodSignature} is a extension function in Kotlin
 * <p>
 * Example: MethodSignatureBuilder().atClass("...").extensionFunction(<receiverType>)
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureExtensionFunctionEnabled {
    private final MethodSignatureImpl methodSignature;

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    protected MethodSignatureExtensionFunctionEnabled(
            String receiverType,
            MethodSignatureImpl methodSignature,
            TypeAliases typeAliases,
            KotlinTypeAliasChecker kotlinTypeAliasChecker,
            boolean isApplyTypeAliases) {
        this.methodSignature = methodSignature;

        this.methodSignature.getParametersType().add(0, KotlinDataTypeTransformer.transform(receiverType));
        this.methodSignature.makeExtensionFunction();

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = kotlinTypeAliasChecker;
        this.isApplyTypeAliases = isApplyTypeAliases;
    }

    public MethodSignatureWithClassAndReturn returns(String returnType) {
        Objects.requireNonNull(returnType, "given returnType to returns() is null");

        String originalReturnType = returnType;

        if (isApplyTypeAliases) {
            originalReturnType = kotlinTypeAliasChecker.getOriginalTypeName(returnType, typeAliases);

            if (originalReturnType == null) {
                throw new CyclicTypeAliasException(returnType);
            }
        }

        return new MethodSignatureWithClassAndReturn(
                originalReturnType,
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }
}
