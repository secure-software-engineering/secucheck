package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithInParamThisObj {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithInParamThisObj(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithInAndOut out() {
        return new MethodWithInAndOut(new OutputDeclarationImpl(), method);
    }

    public Method configure() {
        return method;
    }

    public MethodWithInRemainingParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return new MethodWithInRemainingParam(inputDeclaration, method);
    }
}
