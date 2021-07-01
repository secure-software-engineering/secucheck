package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with class name operator.
 * 
 * Example: MethodSignatureConfigurator().atClass("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClass {

	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClass(String methodClass, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setClassOfMethodSign(methodClass);
	}
	
	public MethodSignatureWithClassAndReturn returns(String methodReturn) {
		return new MethodSignatureWithClassAndReturn(methodReturn, methodSignature);
	}

}
