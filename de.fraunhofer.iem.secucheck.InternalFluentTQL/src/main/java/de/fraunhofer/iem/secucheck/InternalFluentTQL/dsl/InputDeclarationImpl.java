package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.ArrayList;
import java.util.List;

class InputDeclarationImpl implements InputDeclaration {
    private List<Input> inputs = new ArrayList<Input>();

    public List<Input> getInputs() {
        return inputs;
    }

    public void addInput(Input intput) {
        if (intput instanceof ThisObjectImpl) {
            for (Input itr : inputs) {
                if (itr instanceof ThisObject)
                    return;
            }

            inputs.add(new ThisObjectImpl());
        } else {
            inputs.add(intput);
        }
    }
}
