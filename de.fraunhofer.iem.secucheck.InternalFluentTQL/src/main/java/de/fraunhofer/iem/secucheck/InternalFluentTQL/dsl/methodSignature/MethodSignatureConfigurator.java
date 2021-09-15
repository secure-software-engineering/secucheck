package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * Configurator for the {@link MethodSignature}
 *
 * @author Enri Ozuni
 */
public class MethodSignatureConfigurator {
    private final MethodSignatureImpl methodSignature;

    public MethodSignatureConfigurator() {
        methodSignature = new MethodSignatureImpl();
    }

    public MethodSignatureWithClass atClass(String fullyQualifiedClassName) {
        return new MethodSignatureWithClass(fullyQualifiedClassName, methodSignature);
    }
}
