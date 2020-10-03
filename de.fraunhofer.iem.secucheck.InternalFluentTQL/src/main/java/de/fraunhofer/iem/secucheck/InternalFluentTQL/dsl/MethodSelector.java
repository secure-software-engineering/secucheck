package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

public class MethodSelector implements Method {
    private String methodSignature;
    private MethodSet methodSet;
    private InputDeclaration inputDeclaration = null;
    private OutputDeclaration outputDeclaration = null;

    public String getSignature() {
        return methodSignature;
    }

    public void setSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public MethodSet getMethodSet() {
        return methodSet;
    }

    public void setMethodSet(MethodSet methodSet) {
        this.methodSet = methodSet;
    }

    public InputDeclaration getInputDeclaration() {
        return inputDeclaration;
    }

    public void setInputDeclaration(InputDeclaration inputDeclaration) {
        this.inputDeclaration = inputDeclaration;
    }

    public OutputDeclaration getOutputDeclaration() {
        return outputDeclaration;
    }

    public void setOutputDeclaration(OutputDeclaration outputDeclaration) {
        this.outputDeclaration = outputDeclaration;
    }

    public MethodSelector(String methodSignature) {
        this.methodSignature = methodSignature;
    }
}
