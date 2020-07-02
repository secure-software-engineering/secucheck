package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithOutParam {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithOutParam(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithOutParam param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return this;
    }

    public MethodWithOutParamThisObj thisObject() {
        outputDeclaration.addOutput(new ThisObjectImpl());
        return new MethodWithOutParamThisObj(outputDeclaration, method);
    }

    public MethodWithOutParamReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithOutParamReturn(outputDeclaration, method);
    }

    public Method configure() {
        return method;
    }

    public MethodWithIn in() {
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }
}
