package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

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
        methodSignature = new MethodSignatureImpl();

        this.typeAliases = typeAliases;
        this.kotlinTypeAliasChecker = new KotlinTypeAliasChecker();
        this.isApplyTypeAliases = true;
    }

    public MethodSignatureWithClass atClass(String fullyQualifiedClassName) {
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
}
