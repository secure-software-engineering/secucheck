package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.EntryPoint.EntryPoint;
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
		this.methodEntryPointName = methodEntryPointName;
	}
	
}
