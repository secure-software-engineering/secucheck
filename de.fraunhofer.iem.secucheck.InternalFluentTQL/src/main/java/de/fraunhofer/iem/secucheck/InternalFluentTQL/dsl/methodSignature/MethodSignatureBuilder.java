package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

import java.util.Objects;

/**
 * Configurator for the {@link MethodSignature}
 *
 * @author Ranjith Krishnamurthy
 * @author Enri Ozuni
 */
public class MethodSignatureBuilder {
    private final MethodSignatureImpl methodSignature;

    private final TypeAliases typeAliases;
    private final KotlinTypeAliasChecker kotlinTypeAliasChecker;
    private final boolean isApplyTypeAliases;

    public MethodSignatureBuilder() {
        methodSignature = new MethodSignatureImpl();

        this.typeAliases = null;
        this.kotlinTypeAliasChecker = null;
        this.isApplyTypeAliases = false;
    }

    public MethodSignatureBuilder(TypeAliases typeAliases) {
        Objects.requireNonNull(typeAliases, "given typeAliases to MethodSignatureBuilder() is null");

        methodSignature = new MethodSignatureImpl();

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = new KotlinTypeAliasChecker();
        this.isApplyTypeAliases = true;
    }

    public MethodSignatureWithClass atClass(String fullyQualifiedClassName) {
        Objects.requireNonNull(fullyQualifiedClassName, "given fullyQualifiedClassName to atClass() is null");

        String originalFullyQualifiedClassName = fullyQualifiedClassName;

        if (isApplyTypeAliases) {
            originalFullyQualifiedClassName = kotlinTypeAliasChecker.getOriginalTypeName(fullyQualifiedClassName, typeAliases);

            if (originalFullyQualifiedClassName == null) {
                throw new CyclicTypeAliasException(fullyQualifiedClassName);
            }
        }

        return new MethodSignatureWithClass(
                originalFullyQualifiedClassName,
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }

    /**
     * This is for the top level member and forms the fully qualified class name in JVM
     *
     * @param fileName    File name in which the method is defined as top level member
     * @param packageName Package name in which the method is defined as top level member
     * @return MethodSignatureWithClass
     */
    public MethodSignatureWithClass topLevelMember(String fileName,
                                                   String packageName) {
        Objects.requireNonNull(fileName, "given fileName to topLevelMember() is null");
        Objects.requireNonNull(packageName, "given packageName to topLevelMember() is null");

        methodSignature.makeTopLevelMember();

        return new MethodSignatureWithClass(
                packageName + "." + fileName + "Kt",
                methodSignature,
                typeAliases,
                kotlinTypeAliasChecker,
                isApplyTypeAliases);
    }
}
