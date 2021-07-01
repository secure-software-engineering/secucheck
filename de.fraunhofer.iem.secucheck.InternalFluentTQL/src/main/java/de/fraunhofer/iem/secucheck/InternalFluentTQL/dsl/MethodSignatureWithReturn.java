package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns operator.
 * 
 * Example: MethodSignatureConfigurator().returns("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithReturn {
	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithReturn(String returns, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setReturnType(returns);
	}
	
	public MethodSignatureWithReturnsAndName named(String name) {
		return new MethodSignatureWithReturnsAndName(name, methodSignature);
	}
}
