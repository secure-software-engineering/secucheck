package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns and name operators.
 * Example: MethodSignatureConfigurator().returns("...").named("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithReturnsAndName {
	private MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithReturnsAndName(String name, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setMethodName(name);
	}
	
	public MethodSignatureWithReturnsAndNameAndParam accepts(String param) {
		methodSignature.setMethodParameters(param);
		return new MethodSignatureWithReturnsAndNameAndParam(param, methodSignature);
	}
}
