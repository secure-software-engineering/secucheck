package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.MethodSignature;

/**
 * Configurator for the {@link Method}
 *
 * @author Ranjith Krishnamurthy
 * @author Enri Ozuni
 */
public class MethodConfigurator {
    private final MethodImpl method;

    public MethodConfigurator(String methodSignature) {
        //method = new MethodImpl(methodSignature);
        method = new MethodImpl(MethodSelector.getMethodSignatureFromString(methodSignature));
    }

    public MethodConfigurator(MethodSignature methodSignature) {
        method = new MethodImpl(methodSignature);
    }

    public MethodWithIn in() {
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }

    public MethodWithOut out() {
        return new MethodWithOut(new OutputDeclarationImpl(), method);
    }
}
