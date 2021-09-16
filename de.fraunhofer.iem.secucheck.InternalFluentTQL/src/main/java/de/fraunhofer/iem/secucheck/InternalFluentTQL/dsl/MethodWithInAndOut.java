package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This class represents that it contains {@link Method} with complete in operator and started out operator.
 * Example: MethodConfigurator("...").in()...out()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithInAndOut {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOut(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
    }

    public MethodWithInAndOutParam param(int parameterID) {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction()));

        return new MethodWithInAndOutParam(outputDeclaration, method);
    }

    public MethodWithInAndOutReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithInAndOutReturn(outputDeclaration, method);
    }

    public MethodWithInAndOutThisObj thisObject() {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getDefaultOutputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithInAndOutThisObj(outputDeclaration, method);
    }

    public MethodWithInAndOutThisObj thisObject(QualifiedThis qualifiedThis) {
        outputDeclaration.addOutput(ExtensionFunctionUtility.getQualifiedThisOutPut(qualifiedThis, method));
        return new MethodWithInAndOutThisObj(outputDeclaration, method);
    }
}
