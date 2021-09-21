package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

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
        method = new MethodImpl(MethodSelector.getMethodSignatureFromString(methodSignature, null));
    }

    public MethodConfigurator(String methodSignature, TypeAliases typeAliases) {
        //method = new MethodImpl(methodSignature);
        method = new MethodImpl(MethodSelector.getMethodSignatureFromString(methodSignature, typeAliases));
    }

    public MethodConfigurator(MethodSignature methodSignature) {
        method = new MethodImpl(methodSignature);
    }

    public MethodConfigurator(MethodSignature methodSignature, TypeAliases typeAliases) {
        MethodSignature methodSignatureWithTypeAliases = new MethodSignatureBuilder(typeAliases)
                .atClass(methodSignature.getFullyQualifiedClassName())
                .returns(methodSignature.getReturnType())
                .named(methodSignature.getMethodName())
                .parameter(methodSignature.getParametersType())
                .configure();

        method = new MethodImpl(methodSignatureWithTypeAliases);
    }

    public MethodWithIn in() {
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }

    public MethodWithOut out() {
        return new MethodWithOut(new OutputDeclarationImpl(), method);
    }
}
