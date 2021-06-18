package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

/**
 * Configurator for the MethodSignature
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
