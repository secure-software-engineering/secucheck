package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

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
	
	public MethodSignatureWithClassAndReturn(MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
	}
	
	public MethodSignatureWithClassAndReturnAndName named(String methodName) {
		Objects.requireNonNull(methodName, "named() method's argument is null.");
		this.methodSignature.setNameOfMethodSign(methodName);
		return new MethodSignatureWithClassAndReturnAndName(methodSignature);
	}
}
