package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of InputDeclaration
 *
 * @author Ranjith Krishnamurthy
 */
class InputDeclarationImpl implements InputDeclaration {
    private final List<Input> inputs = new ArrayList<>();

    public List<Input> getInputs() {
        return inputs;
    }

    public void addInput(Input input) {
        if (input instanceof ThisObjectImpl) {
            for (Input itr : inputs) {
                if (itr instanceof ThisObject)
                    return;
            }

            inputs.add(new ThisObjectImpl());
        } else {
            inputs.add(input);
        }
    }
}
