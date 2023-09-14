package de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput;

import java.util.List;
import java.util.Set;

/**
 * Interface for InputDeclaration
 *
 * @author Ranjith Krishnamurthy
 */
public interface InputDeclaration {
    /**
     * Returns the List of Input
     *
     * @return List of Input
     */
    List<Input> getInputs();

    /**
     * Returns the List of Input as Set
     *
     * @return List of Input as Set
     */
    Set<Input> getInputsAsSet();
}
