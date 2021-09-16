package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This class represents that it contains {@link Method} with in operator.
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
        inputDeclaration.addInput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction()));
        return new MethodWithInParam(inputDeclaration, method);
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
