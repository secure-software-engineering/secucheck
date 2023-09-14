package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.MethodSignature;

/**
 * Implementation of the {@link Method}
 *
 * @author Ranjith Krishnamurthy
 * @author Enri Ozuni
 */
class MethodImpl implements Method {
    private String signature;
    private MethodSignature methodSignature;
    private MethodSet methodSet;
    private InputDeclaration inputDeclaration = new InputDeclarationImpl();
    private OutputDeclaration outputDeclaration = new OutputDeclarationImpl();

    public String getSignature() {
        return signature;
    }

    public void setSignature(String methodSignature) {
        this.signature = methodSignature;
    }

    public MethodSignature getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(MethodSignature methodSignature) {
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
        this.signature = methodSignature;
    }

    public MethodImpl(MethodSignature methodSignature) {
        this.methodSignature = methodSignature;
        this.signature = this.methodSignature.getCompleteMethodSignature();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((signature == null) ? 0 : signature.hashCode());
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
        if (signature == null) {
            if (other.getSignature() != null)
                return false;
        } else if (!signature.equals(other.getSignature()))
            return false;

        if (methodSignature == null) {
            if (other.getMethodSignature() != null)
                return false;
        } else if (!methodSignature.equals(other.getMethodSignature()))
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
        StringBuilder str = null;
        if (signature != null) {
            str = new StringBuilder("Method(\"" + methodSignature + "\")\n      ");
        } else {
            str = new StringBuilder("Method(\"" + signature + "\")\n      ");
        }

        if (inputDeclaration != null)
            str.append(".").append(inputDeclaration.toString()).append("\n      ");

        if (outputDeclaration != null)
            str.append(".").append(outputDeclaration.toString());

        return str.toString();
    }
}
