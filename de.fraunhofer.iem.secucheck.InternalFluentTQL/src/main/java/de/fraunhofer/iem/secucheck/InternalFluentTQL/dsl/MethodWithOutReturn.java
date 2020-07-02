package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodWithOutReturn {
    private final MethodImpl method;
    private OutputDeclarationImpl outputDeclaration;

    public MethodWithOutReturn(OutputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = inputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithOutThisObjReturnParam thisObject() {
        outputDeclaration.addOutput(new ThisObjectImpl());
        return new MethodWithOutThisObjReturnParam(outputDeclaration, method);
    }

    public MethodWithOutParamReturn param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return new MethodWithOutParamReturn(outputDeclaration, method);
    }

    public Method configure() {
        return method;
    }

    public MethodWithIn in() {
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }
}
