package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

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
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return this;
    }

    public MethodWithOutAndInThisObj thisObject() {
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getDefaultInputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithOutAndInThisObj(inputDeclaration, method);
    }

    public MethodWithOutAndInThisObj thisObject(QualifiedThis qualifiedThis) {
        inputDeclaration.addInput(ExtensionFunctionUtility.getQualifiedThisInput(qualifiedThis, method));
        return new MethodWithOutAndInThisObj(inputDeclaration, method);
    }
}
