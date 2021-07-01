package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns and name operators.
 * Example: MethodSignatureConfigurator().returns("...").named("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturnAndName {
	private MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClassAndReturnAndName(String methodName, MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
		this.methodSignature.setNameOfMethodSign(methodName);
	}
	
	public MethodSignatureWithClassAndReturnAndNameAndParam accepts(String methodParam) {
		return new MethodSignatureWithClassAndReturnAndNameAndParam(methodParam, methodSignature);
	}
}
