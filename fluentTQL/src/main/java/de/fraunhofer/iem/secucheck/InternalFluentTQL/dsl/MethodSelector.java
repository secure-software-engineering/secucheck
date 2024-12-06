package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.InvalidMethodSignatureException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to instantiate the {@link Method}, that can be annotated with the FluentTQL annotation to configure the method
 * for the taint flow.
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSelector implements Method {
    private String signature;
    private MethodSignature methodSignature;
    private MethodSet methodSet;
    private InputDeclaration inputDeclaration = new InputDeclarationImpl();
    private OutputDeclaration outputDeclaration = new OutputDeclarationImpl();

    /**
     * This returns the method signature.
     *
     * @return Method signature.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Setter for the Method signature.
     *
     * @param methodSignature Method signature.
     */
    public void setSignature(String methodSignature) {
        Objects.requireNonNull(methodSignature);

        this.signature = methodSignature;
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

        this.signature = methodSignature;
        this.methodSignature = getMethodSignatureFromString(methodSignature);
    }

    protected static MethodSignature getMethodSignatureFromString(String signature) throws InvalidMethodSignatureException {
        String[] temp = signature.split(":");

        if (temp.length != 2) {
            throw new InvalidMethodSignatureException(signature);
        }

        String fullyQualifiedClassName = temp[0].replaceAll("\\s+", "");
        String remainingString = temp[1].trim();

        temp = remainingString.split(" ");

        if (temp.length < 2) {
            throw new InvalidMethodSignatureException(signature);
        }

        String returnType = temp[0];
        temp[0] = "";
        remainingString = String.join("", temp).trim();

        temp = remainingString.split("\\(");

        if (temp.length != 2) {
            throw new InvalidMethodSignatureException(signature);
        }

        String methodName = temp[0].replaceAll("\\s+", "");
        remainingString = temp[1].replaceAll("\\s+", "");

        if (remainingString.charAt(remainingString.length() - 1) != ')') {
            throw new InvalidMethodSignatureException(signature);
        }

        //TODO: Currently this replaces all the occurrence of ) to empty
        // Change this to below implementation
        // Replace only the last character that should be ) to empty
        // Then check each element i.e. fullyQualifiedClassName, returnType, methodName and parametersType should be valid
        // i.e. it should not contains any invalid character
        // Do this here or in the MethodSignature
        remainingString = remainingString.replace(")", "");

        temp = remainingString.split(",");

        List<String> parametersType = new ArrayList<>();

        for (String elem : temp) {
            parametersType.add(elem.replaceAll("\\s+", ""));
        }

        return new MethodSignatureBuilder()
                .atClass(fullyQualifiedClassName)
                .returns(returnType)
                .named(methodName)
                .parameter(parametersType)
                .configure();
    }

    @Override
    public MethodSignature getMethodSignature() {
        return this.methodSignature;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((signature == null) ? 0 : signature.hashCode());
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
        if (signature == null) {
            if (other.getSignature() != null)
                return false;
        } else if (!signature.equals(other.getSignature()))
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
        StringBuilder str = new StringBuilder("Method(\"" + signature + "\")\n      ");

        if (inputDeclaration != null)
            str.append(".").append(inputDeclaration.toString()).append("\n      ");

        if (outputDeclaration != null)
            str.append(".").append(outputDeclaration.toString());

        return str.toString();
    }
}
