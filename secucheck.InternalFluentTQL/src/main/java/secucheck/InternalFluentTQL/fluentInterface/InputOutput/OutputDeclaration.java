package secucheck.InternalFluentTQL.fluentInterface.InputOutput;

import java.util.List;
import java.util.Set;

/**
 * Interface for OutputDeclaration
 *
 */
public interface OutputDeclaration {
    /**
     * Returns the List of Output
     *
     * @return List of Output
     */
    List<Output> getOutputs();

    /**
     * Returns the List of Output as Set
     *
     * @return List of Output as Set
     */
    Set<Output> getOutputsAsSet();
}
