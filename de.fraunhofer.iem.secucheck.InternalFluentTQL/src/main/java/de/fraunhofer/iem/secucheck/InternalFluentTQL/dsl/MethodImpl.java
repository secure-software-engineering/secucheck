package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;

/**
 * Implementation of the Method
 *
 * @author Ranjith Krishnamurthy
 */
class MethodImpl implements Method {
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

    public MethodImpl(String methodSignature) {
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

        MethodImpl other = (MethodImpl) obj;
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
}
