package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This class represents that it contains {@link Method} with complete in operator and out operator with param.
 * Example: MethodConfigurator("...").in()...out().param(...)
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithInAndOutParam {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithInAndOutParam(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithInAndOutParam param(int parameterID) {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return this;
    }

    public MethodWithInAndOutParamThisObj thisObject() {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getDefaultOutputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithInAndOutParamThisObj(outputDeclaration, method);
    }

    public MethodWithInAndOutParamThisObj thisObject(QualifiedThis qualifiedThis) {
        outputDeclaration.addOutput(ExtensionFunctionUtility.getQualifiedThisOutPut(qualifiedThis, method));
        return new MethodWithInAndOutParamThisObj(outputDeclaration, method);
    }

    public MethodWithInAndOutParamReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithInAndOutParamReturn(outputDeclaration, method);
    }

    public Method configure() {
        method.setOutputDeclaration(outputDeclaration);
        return method;
    }
}
