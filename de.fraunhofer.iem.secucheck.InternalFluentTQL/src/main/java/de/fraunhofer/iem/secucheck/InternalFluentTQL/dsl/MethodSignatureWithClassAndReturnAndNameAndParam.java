package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns, name, and accepts operators.
 * Example: MethodSignatureConfigurator().returns("...").named("...").accepts("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturnAndNameAndParam {
	private MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClassAndReturnAndNameAndParam(String methodParam, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setParamOfMethodSign(methodParam);
	}
	
	public MethodSignature configure() {
		return methodSignature;
	}
}
