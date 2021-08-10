package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains Method with complete out and in operator with param value and this object.
 * Example: MethodConfigurator("...").out()...in().param(...)...thisObject()
 *
 */
public class MethodWithOutAndInParamThisObj {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithOutAndInParamThisObj(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public Method configure() {
        method.setInputDeclaration(inputDeclaration);
        return method;
    }

    public MethodWithOutAndInRemainingParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return new MethodWithOutAndInRemainingParam(inputDeclaration, method);
    }
}
