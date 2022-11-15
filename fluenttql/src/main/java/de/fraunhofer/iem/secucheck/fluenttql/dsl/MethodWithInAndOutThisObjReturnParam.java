package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with in and out operator with this object and return value.
 * Example: MethodConfigurator("...").in()...out().thisObject().returnValue()
 *
 * @author Ranjith Krishnamurthy
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
