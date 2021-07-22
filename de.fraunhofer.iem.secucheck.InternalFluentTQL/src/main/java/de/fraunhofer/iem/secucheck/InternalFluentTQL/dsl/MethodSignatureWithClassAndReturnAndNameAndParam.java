package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns, name, and accepts operators.
 * Example: MethodSignatureConfigurator().atClass("...").returns("...").named("...").accepts("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturnAndNameAndParam {
	private MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClassAndReturnAndNameAndParam(MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
	}
	
	public MethodSignature configure() {
		return methodSignature;
	}
}
