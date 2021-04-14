package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of OutputDeclaration
 *
 * @author Ranjith Krishnamurthy
 */
class OutputDeclarationImpl implements OutputDeclaration {
    private final List<Output> outputs = new ArrayList<>();

    public List<Output> getOutputs() {
        return outputs;
    }

    public void addOutput(Output output) {
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
