package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * This method represents that it contains {@link Method} with complete out with param value.
 * Example: MethodConfigurator("...").out().param(...)
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodWithOutParam {
    private final MethodImpl method;
    private final OutputDeclarationImpl outputDeclaration;

    public MethodWithOutParam(OutputDeclarationImpl outputDeclaration, MethodImpl method) {
        this.method = method;
        this.outputDeclaration = outputDeclaration;
        method.setOutputDeclaration(this.outputDeclaration);
    }

    public MethodWithOutParam param(int parameterID) {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getCorrectParameterID(parameterID, method.getMethodSignature().isExtensionFunction())
        );
        return this;
    }

    public MethodWithOutParamThisObj thisObject() {
        outputDeclaration.addOutput(
                ExtensionFunctionUtility.getDefaultOutputThisObjectForExtension(method.getMethodSignature().isExtensionFunction())
        );
        return new MethodWithOutParamThisObj(outputDeclaration, method);
    }

    public MethodWithOutParamThisObj thisObject(QualifiedThis qualifiedThis) {
        outputDeclaration.addOutput(ExtensionFunctionUtility.getQualifiedThisOutPut(qualifiedThis, method));
        return new MethodWithOutParamThisObj(outputDeclaration, method);
    }

    public MethodWithOutParamReturn returnValue() {
        outputDeclaration.addOutput(new ReturnImpl());
        return new MethodWithOutParamReturn(outputDeclaration, method);
    }

    public Method configure() {
        method.setOutputDeclaration(outputDeclaration);
        return method;
    }

    public MethodWithIn in() {
        method.setOutputDeclaration(outputDeclaration);
        return new MethodWithIn(new InputDeclarationImpl(), method);
    }
}
