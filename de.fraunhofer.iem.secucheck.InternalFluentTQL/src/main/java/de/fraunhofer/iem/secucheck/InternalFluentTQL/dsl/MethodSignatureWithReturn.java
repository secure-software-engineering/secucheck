package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

/**
 * This class represents that it contains MethodSignature with returns operator
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
