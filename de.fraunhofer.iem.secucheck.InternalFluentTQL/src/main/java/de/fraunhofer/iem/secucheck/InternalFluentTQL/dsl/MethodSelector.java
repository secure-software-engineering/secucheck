package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.InvalidMethodSignatureException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.kotlinDataTypeTransformerUtility.KotlinDataTypeTransformer;

import java.util.ArrayList;
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
    private final InputDeclaration inputDeclaration = new InputDeclarationImpl();
    private final OutputDeclaration outputDeclaration = new OutputDeclarationImpl();

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

        this.methodSignature = getMethodSignatureFromString(methodSignature, null);
        this.signature = this.methodSignature.getCompleteMethodSignature();
    }

    /**
     * Constructors that sets the method signature with the given type aliases.
     *
     * @param methodSignature Method Signature
     * @param typeAliases     Kotlin Type aliases
     */
    public MethodSelector(String methodSignature, TypeAliases typeAliases) {
        Objects.requireNonNull(methodSignature, "methodSignature is null in MethodSelector constructor.");

        this.methodSignature = getMethodSignatureFromString(methodSignature, typeAliases);
        this.signature = this.methodSignature.getCompleteMethodSignature();
    }

    /**
     * Constructors that sets the method signature object.
     *
     * @param methodSignature Method Signature object
     */
    public MethodSelector(MethodSignature methodSignature) {
        Objects.requireNonNull(methodSignature, "methodSignature is null in MethodSelector constructor.");

        this.methodSignature = methodSignature;
        this.signature = this.methodSignature.getCompleteMethodSignature();
    }

    /**
     * Constructors that sets the method signature object with the given type aliases.
     *
     * @param methodSignature Method Signature object
     * @param typeAliases     Kotlin Type aliases
     */
    public MethodSelector(MethodSignature methodSignature, TypeAliases typeAliases) {
        Objects.requireNonNull(methodSignature, "methodSignature is null in MethodSelector constructor.");
        Objects.requireNonNull(methodSignature, "typeAliases is null in MethodSelector constructor.");

        this.methodSignature = new MethodSignatureBuilder(typeAliases)
                .atClass(methodSignature.getFullyQualifiedClassName())
                .returns(methodSignature.getReturnType())
                .named(methodSignature.getMethodName())
                .parameter(methodSignature.getParametersType())
                .configure();
        this.signature = this.methodSignature.getCompleteMethodSignature();
    }

    protected static MethodSignature getMethodSignatureFromString(String signature, TypeAliases typeAliases) {
        int colonIndex = signature.indexOf(":");
        int openParenthesesIndex = signature.indexOf("(");
        int closeParenthesesIndex = signature.lastIndexOf(")");

        if (colonIndex == -1 || // If you support sub-signature then we need to remove this sub-condition
                colonIndex == 0 ||
                openParenthesesIndex == -1 ||
                colonIndex >= openParenthesesIndex ||
                signature.charAt(signature.length() - 1) != ')') {
            throw new InvalidMethodSignatureException(signature);
        }

        String[] temp = signature.toString().split(":");

        if (temp.length != 2) {
            throw new InvalidMethodSignatureException(signature);
        }

        // FullyQualifiedClassName
        String fullyQualifiedClassName = temp[0].replaceAll("\\s++", "");

        if (temp[1].replaceAll("\\s++", "").startsWith("(")) {
            openParenthesesIndex = signature.indexOf("(", openParenthesesIndex + 1);
        }

        if (signature.charAt(openParenthesesIndex - 1) == ' ') {
            throw new InvalidMethodSignatureException(signature);
        }

        String temporary = signature.
                substring(colonIndex + 1, openParenthesesIndex)
                .replaceAll("\\s++", "<S_R>");

        int lastIndex = temporary.lastIndexOf("<S_R>");

        temporary = (
                temporary.substring(0, lastIndex) +
                        "," +
                        temporary.substring(lastIndex + 5)
        ).replaceAll("<S_R>", "");

        temp = KotlinDataTypeTransformer.replaceFunctionTypeForReturnType(temporary)
                .split(",");

        if (temp.length != 2) {
            throw new InvalidMethodSignatureException(signature);
        }

        // ReturnType
        String returnType = temp[0];
        // MethodName
        String methodName = temp[1];

        String parameters = signature.substring(openParenthesesIndex + 1, closeParenthesesIndex);

        parameters = KotlinDataTypeTransformer.replaceFunctionType(parameters);

        temp = parameters.split(",");

        // Parameters Type
        List<String> parametersType = new ArrayList<>();

        for (String elem : temp) {
            parametersType.add(elem.replaceAll("\\s+", ""));
        }

        if (typeAliases == null) {
            return new MethodSignatureBuilder()
                    .atClass(fullyQualifiedClassName)
                    .returns(returnType)
                    .named(methodName)
                    .parameter(parametersType)
                    .configure();
        } else {
            return new MethodSignatureBuilder(typeAliases)
                    .atClass(fullyQualifiedClassName)
                    .returns(returnType)
                    .named(methodName)
                    .parameter(parametersType)
                    .configure();
        }
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
