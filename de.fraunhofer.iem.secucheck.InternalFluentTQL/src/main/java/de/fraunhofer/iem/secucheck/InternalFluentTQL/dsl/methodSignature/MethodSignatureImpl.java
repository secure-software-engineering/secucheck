package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link MethodSignature}
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
class MethodSignatureImpl implements MethodSignature {
    private String fullyQualifiedClassName;
    private String returnType;
    private String methodName;
    private final List<String> parametersType = new ArrayList<String>();
    private boolean isTopLevelMember = false;
    private boolean isExtensionFunction = false;

    @Override
    public String getCompleteMethodSignature() {
        StringBuilder str = new StringBuilder(fullyQualifiedClassName +
                ": " + returnType + " " + methodName + "(");

        for (String parameterType : parametersType) {
            str.append(parameterType).append(",");
        }

        if (parametersType.size() >= 1) {
            str.deleteCharAt(str.length() - 1);
        }

        str.append(")");

        return str.toString();
    }

    @Override
    public boolean isTopLevelMember() {
        return isTopLevelMember;
    }

    @Override
    public boolean isExtensionFunction() {
        return isExtensionFunction;
    }

    @Override
    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    @Override
    public String getReturnType() {
        return returnType;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public List<String> getParametersType() {
        return parametersType;
    }

    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void addParametersType(List<String> parametersType) {
        this.parametersType.addAll(parametersType);
    }

    public void addParameterType(String parametersType) {
        this.parametersType.add(parametersType);
    }

    public void makeTopLevelMember() {
        this.isTopLevelMember = true;
    }

    public void makeExtensionFunction() {
        this.isExtensionFunction = true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fullyQualifiedClassName == null) ? 0 : fullyQualifiedClassName.hashCode());
        result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + parametersType.hashCode();
        result = prime * result + Boolean.valueOf(isTopLevelMember).hashCode();
        result = prime * result + Boolean.valueOf(isExtensionFunction).hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        MethodSignatureImpl other = (MethodSignatureImpl) obj;

        if (isTopLevelMember != other.isTopLevelMember) {
            return false;
        }

        if (isExtensionFunction != other.isExtensionFunction) {
            return false;
        }

        if (returnType == null) {
            if (other.getReturnType() != null)
                return false;
        } else if (!returnType.equals(other.getReturnType()))
            return false;

        if (methodName == null) {
            if (other.getMethodName() != null)
                return false;
        } else if (!methodName.equals(other.getMethodName()))
            return false;

        return parametersType.equals(other.getParametersType());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("MethodSignature<" + getCompleteMethodSignature() + ">");

        if (isTopLevelMember) {
            stringBuilder.append("\n               .topLevelMember()");
        }

        if (isExtensionFunction) {
            stringBuilder.append("\n               .extensionFunction()");
        }

        return stringBuilder.toString();
    }
}
