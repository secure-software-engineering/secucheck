package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out with this object and return value.
 * Example: MethodConfigurator("...").out().thisObject().returnValue()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithOutThisObjReturnParam {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithOutThisObjReturnParam(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithOutThisObjReturnParam param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return this;
    }

    public Method configure() {
        method.setOutputDeclaration(outputDeclaration);
        return method;
    }

    public MethodWithIn in() {
        method.setOutputDeclaration(outputDeclaration);
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }
}
