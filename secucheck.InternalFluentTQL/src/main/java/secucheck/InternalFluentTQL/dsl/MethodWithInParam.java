package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains Method with in and param value.
 * Example: MethodConfigurator("...").in().param(...)
 *
 */
public class MethodWithInParam {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithInParam(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithInAndOut out() {
        method.setInputDeclaration(inputDeclaration);
        return new MethodWithInAndOut(new OutputDeclarationImpl(), method);
    }

    public Method configure() {
        method.setInputDeclaration(inputDeclaration);
        return method;
    }

    public MethodWithInParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return this;
    }

    public MethodWithInThisObj thisObject() {
        inputDeclaration.addInput(new ThisObjectImpl());
        return new MethodWithInThisObj(inputDeclaration, method);
    }
}
