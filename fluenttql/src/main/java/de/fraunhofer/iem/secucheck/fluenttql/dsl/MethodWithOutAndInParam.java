package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out and in operator with param value.
 * Example: MethodConfigurator("...").out()...in().param(...)
 *
 * @author Ranjith Krishnamurthy
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
