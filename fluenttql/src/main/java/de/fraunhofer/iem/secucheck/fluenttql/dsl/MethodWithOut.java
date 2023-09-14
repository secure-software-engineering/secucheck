package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with out operator.
 * Example: MethodConfigurator("...").out()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithOut {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithOut(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
    }

    public MethodWithOutParam param(int parameterID) {
        outputDeclaration.addOutput(new ParameterImpl(parameterID));
        return new MethodWithOutParam(outputDeclaration, method);
    }

    public MethodWithOutReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithOutReturn(outputDeclaration, method);
    }

    public MethodWithOutThisObj thisObject() {
        outputDeclaration.addOutput(new ThisObjectImpl());
        return new MethodWithOutThisObj(outputDeclaration, method);
    }
}
