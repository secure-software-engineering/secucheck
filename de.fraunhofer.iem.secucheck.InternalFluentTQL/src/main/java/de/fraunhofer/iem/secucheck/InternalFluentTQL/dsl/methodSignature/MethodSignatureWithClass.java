package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.Objects;

/**
 * This class represents that it contains {@link MethodSignature} with class name operator.
 * <p>
 * Example: MethodSignatureBuilder().atClass("...")
 *
 * @author Ranjith Krishnamurthy
 * @author Enri Ozuni
 */
public class MethodSignatureWithClass {
    private final MethodSignatureImpl methodSignature;

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    protected MethodSignatureWithClass(
            String fullyQualifiedClassName,
            MethodSignatureImpl methodSignature,
            TypeAliases typeAliases,
            KotlinTypeAliasChecker kotlinTypeAliasChecker,
            boolean isApplyTypeAliases) {
        this.methodSignature = methodSignature;
        this.methodSignature.setFullyQualifiedClassName(KotlinDataTypeTransformer.transform(fullyQualifiedClassName));

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
                KotlinDataTypeTransformer.replaceFunctionType(originalReturnType),
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }

    public MethodSignatureExtensionFunctionEnabled extensionFunction(String receiverType) {
        Objects.requireNonNull(receiverType, "given receiverType to extensionFunction() is null");

        String originalReceiverType = receiverType;

        if (isApplyTypeAliases) {
            originalReceiverType = kotlinTypeAliasChecker.getOriginalTypeName(originalReceiverType, typeAliases);

            if (originalReceiverType == null) {
                throw new CyclicTypeAliasException(receiverType);
            }
        }

        return new MethodSignatureExtensionFunctionEnabled(
                originalReceiverType,
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }

    public MethodSignatureOfProperty property(String propertyName, String propertyType) {
        Objects.requireNonNull(propertyType, "given propertyType to property() is null");
        Objects.requireNonNull(propertyType, "given propertyName to property() is null");

        String originalPropertyType = propertyType;

        if (isApplyTypeAliases) {
            originalPropertyType = kotlinTypeAliasChecker.getOriginalTypeName(originalPropertyType, typeAliases);

            if (originalPropertyType == null) {
                throw new CyclicTypeAliasException(propertyType);
            }
        }

        return new MethodSignatureOfProperty(
                propertyName,
                KotlinDataTypeTransformer.replaceFunctionType(originalPropertyType),
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases
        );
    }

    public MethodSignatureOfPropertyWithExtensionEnabled extensionProperty(String receiverType, String propertyName, String propertyType) {
        Objects.requireNonNull(receiverType, "given receiverType to extensionProperty() is null");
        Objects.requireNonNull(propertyType, "given propertyType to extensionProperty() is null");
        Objects.requireNonNull(propertyType, "given propertyName to extensionProperty() is null");

        String originalReceiverType = receiverType;

        if (isApplyTypeAliases) {
            originalReceiverType = kotlinTypeAliasChecker.getOriginalTypeName(originalReceiverType, typeAliases);

            if (originalReceiverType == null) {
                throw new CyclicTypeAliasException(receiverType);
            }
        }

        String originalPropertyType = propertyType;

        if (isApplyTypeAliases) {
            originalPropertyType = kotlinTypeAliasChecker.getOriginalTypeName(originalPropertyType, typeAliases);

            if (originalPropertyType == null) {
                throw new CyclicTypeAliasException(propertyType);
            }
        }

        return new MethodSignatureOfPropertyWithExtensionEnabled(
                originalReceiverType,
                propertyName,
                KotlinDataTypeTransformer.replaceFunctionType(originalPropertyType),
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases
        );
    }
}
