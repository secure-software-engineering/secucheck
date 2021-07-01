package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with class and return operator.
 * 
 * Example: MethodSignatureConfigurator().atClass("...").returns("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturn {
	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClassAndReturn(String methodReturn, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setReturnOfMethodSign(methodReturn);
	}
	
	public MethodSignatureWithClassAndReturnAndName named(String methodName) {
		return new MethodSignatureWithClassAndReturnAndName(methodName, methodSignature);
	}
}
