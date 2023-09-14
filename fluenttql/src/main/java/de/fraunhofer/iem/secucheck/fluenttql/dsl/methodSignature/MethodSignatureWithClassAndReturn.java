package de.fraunhofer.iem.secucheck.fluenttql.dsl.methodSignature;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with class and return operator.
 * 
 * Example: MethodSignatureBuilder().atClass("...").returns("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturn {
	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClassAndReturn(String methodReturn, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setReturnType(methodReturn);
	}
	
	public MethodSignatureWithClassAndReturnAndName named(String methodName) {
		return new MethodSignatureWithClassAndReturnAndName(methodName, methodSignature);
	}
}
