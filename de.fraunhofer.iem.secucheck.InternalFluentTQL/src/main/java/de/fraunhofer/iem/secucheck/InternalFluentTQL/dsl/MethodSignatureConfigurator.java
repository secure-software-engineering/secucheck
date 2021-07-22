package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import java.util.Objects;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * Configurator for the {@link MethodSignature}
 *
 * @author Enri Ozuni
 */
public class MethodSignatureConfigurator {
	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureConfigurator() {
		methodSignature = new MethodSignatureImpl();
	}
	
	public MethodSignatureWithClass atClass(String methodClass) {
		Objects.requireNonNull(methodClass, "atClass() method's argument is null.");
		this.methodSignature.setClassOfMethodSign(methodClass);
		return new MethodSignatureWithClass(methodSignature);
	}
}
