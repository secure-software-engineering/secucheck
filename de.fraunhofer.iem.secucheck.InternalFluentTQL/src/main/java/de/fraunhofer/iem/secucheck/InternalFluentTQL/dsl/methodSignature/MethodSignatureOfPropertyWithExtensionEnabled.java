package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

/**
 * This class represents that the {@link MethodSignature} of a property in Kotlin
 * <p>
 * Example: MethodSignatureBuilder().atClass("...").extensionProperty(<receiverType>, </receiverType><propertyName>, <propertyType>)
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureOfPropertyWithExtensionEnabled {
    private final MethodSignatureImpl methodSignature;

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    private final String propertyName;
    private final String propertyType;
    private final String receiverType;

    public MethodSignatureOfPropertyWithExtensionEnabled(
            String receiverType,
            String propertyName,
            String propertyType,
            MethodSignatureImpl methodSignature,
            TypeAliases typeAliases,
            KotlinTypeAliasChecker kotlinTypeAliasChecker,
            boolean isApplyTypeAliases) {
        this.methodSignature = methodSignature;
        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = kotlinTypeAliasChecker;
        this.isApplyTypeAliases = isApplyTypeAliases;
        this.propertyName = propertyName;
        this.propertyType = KotlinDataTypeTransformer.transform(propertyType);
        this.receiverType = receiverType;
        this.methodSignature.getParametersType().add(0, KotlinDataTypeTransformer.transform(this.receiverType));
        this.methodSignature.makeExtensionFunction();
    }

    /**
     * Returns the getter MethodSignature
     *
     * @return getter MethodSignature
     */
    public MethodSignature getter() {
        methodSignature.setMethodName(
                "get" + propertyName.substring(0, 1).toUpperCase() +
                        propertyName.substring(1)
        );
        methodSignature.setReturnType(propertyType);

        return methodSignature;
    }
}
