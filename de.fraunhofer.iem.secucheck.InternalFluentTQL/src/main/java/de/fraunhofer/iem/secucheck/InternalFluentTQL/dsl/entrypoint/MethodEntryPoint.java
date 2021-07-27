package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.entrypoint;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.ImproperEntryPointNameException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.Query.TaintFlowQuery;

/**
 * This class represents an entry point as a Java method that can be added to the entry point list in {@link TaintFlowQuery}
 * 
 * @author Enri Ozuni
 */
public class MethodEntryPoint implements EntryPoint {
	
	private String methodEntryPointName;
	
	public MethodEntryPoint(String methodEntryPointName) {
		Objects.requireNonNull(methodEntryPointName, "Argument of MethodEntryPoint's contructor is null.");
		setMethodEntryPointName(methodEntryPointName);
	}
	
	public MethodEntryPoint(MethodSignature methodEntryPointName) {
		Objects.requireNonNull(methodEntryPointName, "Argument of MethodEntryPoint's contructor is null.");
		String methodEntryPointNameAsStr = methodEntryPointName.getClassOfMethodSign()+": "
				+methodEntryPointName.getReturnOfMethodSign()+" "
				+methodEntryPointName.getNameOfMethodSign()+"("
				+methodEntryPointName.getParamOfMethodSign()+")";
		setMethodEntryPointName(methodEntryPointNameAsStr);
	}
	
	public String getMethodEntryPointName() {
		return methodEntryPointName;
	}
	
	public void setMethodEntryPointName(String methodEntryPointName) {
		Objects.requireNonNull(methodEntryPointName, "Argument of setMethodEntryPointName() method is null.");
		try {
			this.methodEntryPointName = parseMethodEntryPointName(methodEntryPointName);
		} catch (ImproperEntryPointNameException e) {
			e.printStackTrace();
		}
	}
	
	
	private String parseMethodEntryPointName(String methodEntryPointName) throws ImproperEntryPointNameException {
		
		String exceptionMessage = "Entry point name <"+methodEntryPointName+
				"> that was given as method, is not a proper Java method name.";
		
		String parsedMethodEntryPointName = "";
		
		String methodFullyQualifiedName;
		if(methodEntryPointName.contains(":")) {
			String[] subSignatures = methodEntryPointName.split(":");
			if(subSignatures.length == 2) {
				parsedMethodEntryPointName += subSignatures[0].replaceAll("\\s+", "")+":";
				methodFullyQualifiedName = subSignatures[1].trim();
			}
			else {
				throw new ImproperEntryPointNameException(exceptionMessage);
			}
		}
		else {
			throw new ImproperEntryPointNameException(exceptionMessage);
		}
		
		String[] splittedMethodFullyQualifiedName;
		if(methodFullyQualifiedName.matches(".*\\s.*")) {
			splittedMethodFullyQualifiedName = methodFullyQualifiedName.split("\\s+", 2);
			parsedMethodEntryPointName += " "+splittedMethodFullyQualifiedName[0].trim();
		}
		else {
			throw new ImproperEntryPointNameException(exceptionMessage);
		}
		
		String methodNameAndParam = splittedMethodFullyQualifiedName[1].trim();
		if(methodNameAndParam.contains("(") && methodNameAndParam.contains(")")) {
			parsedMethodEntryPointName += " "+methodNameAndParam.substring(0, methodNameAndParam.indexOf("(")).trim();
		}
		else {
			throw new ImproperEntryPointNameException(exceptionMessage);
		}
		
		String methodParam = methodNameAndParam.substring(methodNameAndParam.indexOf("(")+1, methodNameAndParam.indexOf(")"));
		if(StringUtils.isBlank(methodParam)){
			parsedMethodEntryPointName += "()";
		} 
		else {
			parsedMethodEntryPointName += "(";
			String[] methodParamArray = methodParam.split(",");
			int i = 0;
			for(String param : methodParamArray) {
				if(i==methodParamArray.length-1) {
					parsedMethodEntryPointName += param.trim().replaceAll("\\s+", "");
					break;
				}
				parsedMethodEntryPointName += param.trim().replaceAll("\\s+", "")+",";
				i++;
			}
			parsedMethodEntryPointName += ")";
		}
		
		return parsedMethodEntryPointName;
	}
	
}
