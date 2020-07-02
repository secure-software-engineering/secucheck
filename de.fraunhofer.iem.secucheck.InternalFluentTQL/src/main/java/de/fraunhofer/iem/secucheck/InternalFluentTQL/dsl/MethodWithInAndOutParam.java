package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithInAndOutParam {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOutParam(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithInAndOutParam param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return this;
    }

    public MethodWithInAndOutParamThisObj thisObject() {
        outputDeclaration.addOutput(new ThisObjectImpl());
        return new MethodWithInAndOutParamThisObj(outputDeclaration, method);
    }

    public MethodWithInAndOutParamReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithInAndOutParamReturn(outputDeclaration, method);
    }

    public Method configure() {
        return method;
    }
}
