package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with returns and name operators.
 * Example: MethodSignatureConfigurator().atClass("...").returns("...").named("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClassAndReturnAndName {
	private MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClassAndReturnAndName(MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
	}
	
	public MethodSignatureWithClassAndReturnAndNameAndParam accepts(String methodParam) {
		Objects.requireNonNull(methodParam, "accepts() method's argument is null.");
		this.methodSignature.setParamOfMethodSign(methodParam);
		return new MethodSignatureWithClassAndReturnAndNameAndParam(methodSignature);
	}
}
