package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with in and out operator with param and return value.
 * Example: MethodConfigurator("...").in()...out().param(...)...returnValue()
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithInAndOutParamReturn {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOutParamReturn(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithInAndOutThisObjReturnParam thisObject() {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getDefaultOutputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithInAndOutThisObjReturnParam(outputDeclaration, method);
    }

    public MethodWithInAndOutThisObjReturnParam thisObject(QualifiedThis qualifiedThis) {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getQualifiedThisOutPut(qualifiedThis, method)
        );
        return new MethodWithInAndOutThisObjReturnParam(outputDeclaration, method);
    }

    public MethodWithInAndOutParamReturn param(int parameterID) {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return this;
    }

    public Method configure() {
        method.setOutputDeclaration(outputDeclaration);
        return method;
    }
}
