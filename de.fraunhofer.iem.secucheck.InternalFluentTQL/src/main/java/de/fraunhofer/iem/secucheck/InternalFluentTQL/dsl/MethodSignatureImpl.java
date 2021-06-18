package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * Implementation of the MethodSignature
 *
 * @author Enri Ozuni
 */
public class MethodSignatureImpl implements MethodSignature {
	private String methodPackage;
	private String methodReturnType;
	private String methodName;
	private String methodParameters;
	
	public MethodSignatureImpl(String packageName) {
		this.methodPackage = packageName;
	}
	
	@Override
	public String getMethodPackage() {
		return methodPackage;
	}
	
	public void setMethodPackage(String packageName) {
		this.methodPackage = packageName;
	}
	
	@Override
	public String getReturnType() {
		return methodReturnType;
	}
	
	public void setReturnType(String returnType) {
		this.methodReturnType = returnType;
	}

	@Override
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String name) {
		this.methodName = name;
	}

	@Override
	public String getMethodParameters() {
		return methodParameters;
	}
	
	public void setMethodParameters(String parameters) {
		this.methodParameters = parameters;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((methodReturnType == null) ? 0 : methodReturnType.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((methodParameters == null) ? 0 : methodParameters.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        MethodSignatureImpl other = (MethodSignatureImpl) obj;
        if (methodReturnType == null) {
            if (other.getReturnType() != null)
                return false;
        } else if (!methodReturnType.equals(other.getReturnType()))
            return false;
        
        if (methodName == null) {
            if (other.getMethodName() != null)
                return false;
        } else if (!methodName.equals(other.getMethodName()))
            return false;

        if (methodParameters == null) {
            if (other.getMethodParameters() != null)
                return false;
        } else if (!methodParameters.equals(other.getMethodParameters()))
            return false;

        return true;
    }
	
	@Override
    public String toString() {
		StringBuilder str = new StringBuilder("MethodSignature(\"" + methodReturnType + "\")\n      ");
		str.append(".").append(methodName.toString()).append("\n      ");
		str.append(".").append(methodParameters.toString());
		return str.toString();
    }
}
