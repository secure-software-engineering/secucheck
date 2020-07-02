package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

public class MethodWithOutAndIn {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithOutAndIn(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithOutAndInParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return new MethodWithOutAndInParam(inputDeclaration, method);
    }

    public MethodWithOutAndInThisObj thisObject() {
        inputDeclaration.addInput(new ThisObjectImpl());
        return new MethodWithOutAndInThisObj(inputDeclaration, method);
    }
}
