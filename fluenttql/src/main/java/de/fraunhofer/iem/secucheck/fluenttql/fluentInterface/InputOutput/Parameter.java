package de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput;

/**
 * Interface for Parameter
 *
 * @author Ranjith Krishnamurthy
 */
public interface Parameter extends Output, Input {
    /**
     * Returns the Parameter id
     *
     * @return parameter id
     */
    int getParameterId();
}
