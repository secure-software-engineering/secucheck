package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains MethodSignature with returns, name, and accepts operators
 * Example: MethodSignatureConfigurator().returns("...").named("...").accepts("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithReturnsAndNameAndParam {
	private MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithReturnsAndNameAndParam(String param, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setMethodParameters(param);
	}
	
	public MethodSignature configure() {
		return methodSignature;
	}
}
