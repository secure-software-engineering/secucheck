package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out with return value.
 * Example: MethodConfigurator("...").out().returnValue()
 *
 * @author Ranjith Krishnamurthy
 */
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
        method.setOutputDeclaration(outputDeclaration);
        return method;
    }

    public MethodWithIn in() {
        method.setOutputDeclaration(outputDeclaration);
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }
}
