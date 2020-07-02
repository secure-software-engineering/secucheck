package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithInAndOutThisObj {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOutThisObj(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithInAndOutThisObjReturnParam returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithInAndOutThisObjReturnParam(outputDeclaration, method);
    }

    public MethodWithInAndOutParamThisObj param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return new MethodWithInAndOutParamThisObj(outputDeclaration, method);
    }

    public Method configure() {
        return method;
    }
}
