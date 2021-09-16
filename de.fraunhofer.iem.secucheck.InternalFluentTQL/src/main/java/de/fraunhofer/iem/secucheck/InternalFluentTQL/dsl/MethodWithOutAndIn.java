package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out and in operator.
 * Example: MethodConfigurator("...").out()...in()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithOutAndIn {
    private final MethodImpl method;
    private final InputDeclarationImpl inputDeclaration;

    public MethodWithOutAndIn(InputDeclarationImpl inputDeclaration, MethodImpl method) {
        this.method = method;
        this.inputDeclaration = inputDeclaration;
    }

    public MethodWithOutAndInParam param(int parameterID) {
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithOutAndInParam(inputDeclaration, method);
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
