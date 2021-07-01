package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

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
		return new MethodSignatureWithClass(methodClass, methodSignature);
	}
}
