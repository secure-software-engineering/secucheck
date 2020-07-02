package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithOutParamThisObj {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithOutParamThisObj(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithOutThisObjReturnParam returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithOutThisObjReturnParam(outputDeclaration, method);
    }

    public MethodWithOutParamThisObj param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return this;
    }

    public Method configure() {
        return method;
    }

    public MethodWithIn in() {
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }
}
