package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out and in operator with this object.
 * Example: MethodConfigurator("...").out()...in().thisObject()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithOutAndInThisObj {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithOutAndInThisObj(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithOutAndInRemainingParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return new MethodWithOutAndInRemainingParam(inputDeclaration, method);
    }

    public Method configure() {
        method.setInputDeclaration(inputDeclaration);
        return method;
    }
}
