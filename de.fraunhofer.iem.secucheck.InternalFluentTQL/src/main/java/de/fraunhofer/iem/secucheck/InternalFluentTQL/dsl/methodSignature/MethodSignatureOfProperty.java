package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

/**
 * This class represents that the {@link MethodSignature} of a property in Kotlin
 * <p>
 * Example: MethodSignatureBuilder().atClass("...").property(<propertyName>, <propertyType>)
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureOfProperty {
    private final MethodSignatureImpl methodSignature;

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    private final String propertyName;
    private final String propertyType;

    public MethodSignatureOfProperty(
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

    /**
     * Returns the setter MethodSignature
     *
     * @return setter MethodSignature
     */
    public MethodSignature setter() {
        methodSignature.setMethodName(
                "set" + propertyName.substring(0, 1).toUpperCase() +
                        propertyName.substring(1)
        );
        methodSignature.setReturnType("void");
        methodSignature.addParameterType(propertyType);

        return methodSignature;
    }
}
