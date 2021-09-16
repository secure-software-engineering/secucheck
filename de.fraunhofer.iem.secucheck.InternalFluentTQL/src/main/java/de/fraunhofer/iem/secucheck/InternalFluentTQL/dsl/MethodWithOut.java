package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

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
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithOutParam(outputDeclaration, method);
    }

    public MethodWithOutReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithOutReturn(outputDeclaration, method);
    }

    public MethodWithOutThisObj thisObject() {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getDefaultOutputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithOutThisObj(outputDeclaration, method);
    }

    public MethodWithOutThisObj thisObject(QualifiedThis qualifiedThis) {
        outputDeclaration.addOutput(ExtensionFunctionUtility.getQualifiedThisOutPut(qualifiedThis, method));
        return new MethodWithOutThisObj(outputDeclaration, method);
    }
}
