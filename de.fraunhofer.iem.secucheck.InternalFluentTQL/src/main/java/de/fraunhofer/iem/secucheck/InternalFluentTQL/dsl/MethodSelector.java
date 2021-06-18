package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

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
    private InputDeclaration inputDeclaration = new InputDeclarationImpl();
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
        Objects.requireNonNull(methodSignature, "methodSignature is null in MethodSelector constructor.");

        this.methodSignature = methodSignature;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((methodSignature == null) ? 0 : methodSignature.hashCode());
        result = prime * result + ((inputDeclaration == null) ? 0 : inputDeclaration.hashCode());
        result = prime * result + ((outputDeclaration == null) ? 0 : outputDeclaration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        MethodSelector other = (MethodSelector) obj;
        if (methodSignature == null) {
            if (other.getSignature() != null)
                return false;
        } else if (!methodSignature.equals(other.getSignature()))
            return false;

        if (inputDeclaration == null) {
            if (other.getInputDeclaration() != null)
                return false;
        } else if (!inputDeclaration.equals(other.getInputDeclaration()))
            return false;

        if (outputDeclaration == null) {
            if (other.getOutputDeclaration() != null)
                return false;
        } else if (!outputDeclaration.equals(other.getOutputDeclaration()))
            return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Method(\"" + methodSignature + "\")\n      ");

        if (inputDeclaration != null)
            str.append(".").append(inputDeclaration.toString()).append("\n      ");

        if (outputDeclaration != null)
            str.append(".").append(outputDeclaration.toString());

        return str.toString();
    }
    
    // set as unimplemented at the moment
	@Override
	public MethodSignature getMethodSignature() {
		// TODO Auto-generated method stub
		return null;
	}
}
