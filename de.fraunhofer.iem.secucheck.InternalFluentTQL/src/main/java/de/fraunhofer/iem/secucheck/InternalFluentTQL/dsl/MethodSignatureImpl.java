package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * Implementation of the {@link MethodSignature}
 *
 * @author Enri Ozuni
 */
public class MethodSignatureImpl implements MethodSignature {
	private String methodClass;
	private String methodReturn;
	private String methodName;
	private String methodParam;
	
	@Override
	public String getClassOfMethodSign() {
		return methodClass;
	}
	
	public void setClassOfMethodSign(String methodClass) {
		this.methodClass = methodClass;
	}
	
	@Override
	public String getReturnOfMethodSign() {
		return methodReturn;
	}
	
	public void setReturnOfMethodSign(String methodReturn) {
		this.methodReturn = methodReturn;
	}

	@Override
	public String getNameOfMethodSign() {
		return methodName;
	}
	
	public void setNameOfMethodSign(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String getParamOfMethodSign() {
		return methodParam;
	}
	
	public void setParamOfMethodSign(String methodParam) {
		this.methodParam = methodParam;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((methodReturn == null) ? 0 : methodReturn.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((methodParam == null) ? 0 : methodParam.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        MethodSignatureImpl other = (MethodSignatureImpl) obj;
        if (methodReturn == null) {
            if (other.getReturnOfMethodSign() != null)
                return false;
        } else if (!methodReturn.equals(other.getReturnOfMethodSign()))
            return false;
        
        if (methodName == null) {
            if (other.getNameOfMethodSign() != null)
                return false;
        } else if (!methodName.equals(other.getNameOfMethodSign()))
            return false;

        if (methodParam == null) {
            if (other.getParamOfMethodSign() != null)
                return false;
        } else if (!methodParam.equals(other.getParamOfMethodSign()))
            return false;

        return true;
    }
	
	@Override
    public String toString() {
		StringBuilder str = new StringBuilder("MethodSignature(\"" + methodClass + "\")\n      ");
		str.append(".").append(methodReturn.toString()).append("\n      ");
		str.append(".").append(methodName.toString()).append("\n      ");
		str.append(".").append(methodParam.toString());
		return str.toString();
    }
}
