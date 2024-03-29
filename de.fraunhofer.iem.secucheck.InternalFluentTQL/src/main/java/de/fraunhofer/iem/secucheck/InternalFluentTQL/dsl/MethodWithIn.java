package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

/**
 * This class represents that it contains Method with in operator
 * Example: MethodConfigurator("...").in()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithIn {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithIn(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithInParam param(int parameterID) {
        inputDeclaration.addInput(new ParameterImpl(parameterID));
        return new MethodWithInParam(inputDeclaration, method);
    }

    public MethodWithInThisObj thisObject() {
        inputDeclaration.addInput(new ThisObjectImpl());
        return new MethodWithInThisObj(inputDeclaration, method);
    }
}
