package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains Method with in and out operator with this object and return value.
 * Example: MethodConfigurator("...").in()...out().thisObject().returnValue()
 *
 */
public class MethodWithInAndOutThisObjReturnParam {
    private final MethodImpl method;
    private OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOutThisObjReturnParam(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithInAndOutThisObjReturnParam param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return this;
    }

    public Method configure() {
        method.setOutputDeclaration(outputDeclaration);
        return method;
    }
}
