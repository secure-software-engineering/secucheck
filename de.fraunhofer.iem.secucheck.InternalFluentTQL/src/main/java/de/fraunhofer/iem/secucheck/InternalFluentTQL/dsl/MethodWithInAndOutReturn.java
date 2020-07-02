package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithInAndOutReturn {
    private final MethodImpl method;
    private OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOutReturn(OutputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = inputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithInAndOutThisObjReturnParam thisObject() {
        outputDeclaration.addOutput(new ThisObjectImpl());
        return new MethodWithInAndOutThisObjReturnParam(outputDeclaration, method);
    }

    public MethodWithInAndOutParamReturn param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return new MethodWithInAndOutParamReturn(outputDeclaration, method);
    }

    public Method configure() {
        return method;
    }
}
