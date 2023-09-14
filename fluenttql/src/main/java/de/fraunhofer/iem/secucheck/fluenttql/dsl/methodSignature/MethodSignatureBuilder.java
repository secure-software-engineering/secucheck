package de.fraunhofer.iem.secucheck.fluenttql.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.MethodSignature;

/**
 * Configurator for the {@link MethodSignature}
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public class MethodSignatureBuilder {
    private final MethodSignatureImpl methodSignature;

    public MethodSignatureBuilder() {
        methodSignature = new MethodSignatureImpl();
    }

    public MethodSignatureWithClass atClass(String fullyQualifiedClassName) {
        return new MethodSignatureWithClass(fullyQualifiedClassName, methodSignature);
    }
}
