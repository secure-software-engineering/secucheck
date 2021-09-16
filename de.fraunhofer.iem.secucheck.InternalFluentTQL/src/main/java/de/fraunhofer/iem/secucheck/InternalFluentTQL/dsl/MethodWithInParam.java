package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with in and param value.
 * Example: MethodConfigurator("...").in().param(...)
 *
 * @author Ranjith Krishnamurthy
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
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return this;
    }

    public MethodWithInThisObj thisObject() {
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getDefaultInputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithInThisObj(inputDeclaration, method);
    }

    public MethodWithInThisObj thisObject(QualifiedThis qualifiedThis) {
        inputDeclaration.addInput(ExtensionFunctionUtility.getQualifiedThisInput(qualifiedThis, method));
        return new MethodWithInThisObj(inputDeclaration, method);
    }
}
