package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the InputDeclaration.
 *
 * @author Ranjith Krishnamurthy
 */
class InputDeclarationImpl implements InputDeclaration {
    private final List<Input> inputs = new ArrayList<>();

    /**
     * This method returns the List of Inputs.
     *
     * @return List of Inputs.
     */
    public List<Input> getInputs() {
        return inputs;
    }

    /**
     * This method adds a single Input to the list
     *
     * @param input Input
     */
    public void addInput(Input input) {
        Objects.requireNonNull(input, "addInput() method's argument is null.");

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
