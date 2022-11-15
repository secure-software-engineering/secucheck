package de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage;

import java.util.List;

/**
 * Interface for MethodSignature, which is utilized by {@link Method}
 *
 * @author Enri Ozuni
 * @author Ranjith Krishnamurthy
 */
public interface MethodSignature {

    /**
     * Get the package where the method is located
     *
     * @return The package location of method in String format
     */
    String getFullyQualifiedClassName();

    /**
     * Get the return type of the method signature
     *
     * @return The return data type in String format
     */
    String getReturnType();

    /**
     * Get the method name of the method signature
     *
     * @return The method name in String format
     */
    String getMethodName();

    /**
     * Get the parameters that the method signature accepts
     *
     * @return The method parameters in String format
     */
    List<String> getParametersType();

    /**
     * Returns the complete method signature
     *
     * @return Complete method signature
     */
    String getCompleteMethodSignature();
}
