package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out and in operator with just remaining param value, no more return value and this object.
 * Example: MethodConfigurator("...").out()...in()...returnValue()...thisObject()...param()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithOutAndInRemainingParam {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithOutAndInRemainingParam(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithOutAndInRemainingParam param(int parameterID) {
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return this;
    }

    public Method configure() {
        method.setInputDeclaration(inputDeclaration);
        return method;
    }
}
