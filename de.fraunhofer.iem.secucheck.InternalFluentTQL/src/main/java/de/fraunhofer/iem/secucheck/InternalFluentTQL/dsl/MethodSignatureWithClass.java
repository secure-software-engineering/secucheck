package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * This class represents that it contains {@link MethodSignature} with class name operator.
 * 
 * Example: MethodSignatureConfigurator().atClass("...")
 *
 * @author Enri Ozuni
 */
public class MethodSignatureWithClass {

	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureWithClass(MethodSignatureImpl methodSignature) {
		this.methodSignature = methodSignature;
	}
	
	public MethodSignatureWithClassAndReturn returns(String methodReturn) {
		Objects.requireNonNull(methodReturn, "returns() method's argument is null.");
		this.methodSignature.setReturnOfMethodSign(methodReturn);
		return new MethodSignatureWithClassAndReturn(methodSignature);
	}

}
