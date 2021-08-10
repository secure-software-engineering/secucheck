package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains Method with complete out and in operator with param value.
 * Example: MethodConfigurator("...").out()...in().param(...)
 *
 */
public class MethodWithOutAndInParam {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithOutAndInParam(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public Method configure() {
        method.setInputDeclaration(inputDeclaration);
        return method;
    }

    public MethodWithOutAndInParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return this;
    }

    public MethodWithOutAndInThisObj thisObject() {
        inputDeclaration.addInput(new ThisObjectImpl());
        return new MethodWithOutAndInThisObj(inputDeclaration, method);
    }
}
