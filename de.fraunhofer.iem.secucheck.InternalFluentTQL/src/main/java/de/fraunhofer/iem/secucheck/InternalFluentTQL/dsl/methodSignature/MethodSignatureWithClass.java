package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.CyclicTypeAliasException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;
import de.fraunhofer.iem.secucheck.kotlinTypeAliasUtility.KotlinTypeAliasChecker;

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
