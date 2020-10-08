package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the OutputDeclaration.
 *
 * @author Ranjith Krishnamurthy
 */
class OutputDeclarationImpl implements OutputDeclaration {
    private final List<Output> outputs = new ArrayList<>();

    /**
     * This method returns the List of Outputs.
     *
     * @return List of Outputs.
     */
    public List<Output> getOutputs() {
        return outputs;
    }

    /**
     * This method adds a single Output to the list.
     *
     * @param output Output
     */
    public void addOutput(Output output) {
        Objects.requireNonNull(output);

        if (output instanceof ThisObjectImpl) {
            for (Output itr : outputs) {
                if (itr instanceof ThisObject)
                    return;
            }

            outputs.add(new ThisObjectImpl());
        } else if (output instanceof ReturnImpl) {
            for (Output itr : outputs) {
                if (itr instanceof ReturnImpl)
                    return;
            }

            outputs.add(new ReturnImpl());
        } else {
            outputs.add(output);
        }
    }
}
