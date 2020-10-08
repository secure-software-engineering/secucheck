package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

import java.util.Objects;

/**
 * This class is used to instantiate the Method, that can be annotated with the FluentTQL annotation to configure the method
 * for the taint flow.
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSelector implements Method {
    private String methodSignature;
    private MethodSet methodSet;
    protected InputDeclaration inputDeclaration = new InputDeclarationImpl();
    private OutputDeclaration outputDeclaration = new OutputDeclarationImpl();

    /**
     * This returns the method signature.
     *
     * @return Method signature.
     */
    public String getSignature() {
        return methodSignature;
    }

    /**
     * Setter for the Method signature.
     *
     * @param methodSignature Method signature.
     */
    public void setSignature(String methodSignature) {
        Objects.requireNonNull(methodSignature);

        this.methodSignature = methodSignature;
    }

    /**
     * This method returns the MethodSet that the Method belongs to, otherwise it returns null.
     *
     * @return MethodSet
     */
    public MethodSet getMethodSet() {
        return methodSet;
    }

    /**
     * Setter for the MethodSet.
     *
     * @param methodSet MethodSet
     */
    public void setMethodSet(MethodSet methodSet) {
        this.methodSet = methodSet;
    }

    /**
     * This returns the InputDeclaration
     *
     * @return InputDeclaration
     */
    public InputDeclaration getInputDeclaration() {
        return inputDeclaration;
    }

    /**
     * This returns the OutputDeclaration
     *
     * @return OutputDeclaration
     */
    public OutputDeclaration getOutputDeclaration() {
        return outputDeclaration;
    }

    /**
     * Constructors that sets the method signature.
     *
     * @param methodSignature Method Signature
     */
    public MethodSelector(String methodSignature) {
        Objects.requireNonNull(methodSignature);

        this.methodSignature = methodSignature;
    }
}
