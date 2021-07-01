package de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage;

/**
 * Interface for MethodSignature, which is utilized by {@link Method}
 *
 * @author Enri Ozuni
 */
public interface MethodSignature {
	
	/**
     * Get the package where the method is located
     *
     * @return The package location of method in String format
     */
	String getClassOfMethodSign();
	
	/**
     * Get the return type of the method signature
     *
     * @return The return data type in String format
     */
    String getReturnOfMethodSign();
    
    /**
     * Get the method name of the method signature
     *
     * @return The method name in String format
     */
    String getNameOfMethodSign();
    
    /**
     * Get the parameters that the method signature accepts
     *
     * @return The method parameters in String format
     */
    String getParamOfMethodSign();
	
}
