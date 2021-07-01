package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;

/**
 * Configurator for the {@link MethodSignature}
 *
 * @author Enri Ozuni
 */
public class MethodSignatureConfigurator {
	private final MethodSignatureImpl methodSignature;
	
	public MethodSignatureConfigurator(String packageName) {
		methodSignature = new MethodSignatureImpl(packageName);
	}
	
	public MethodSignatureWithReturn returns(String returns) {
		return new MethodSignatureWithReturn(returns, methodSignature);
	}
}
