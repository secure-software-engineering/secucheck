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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + "in".hashCode();
        result = prime * result + inputs.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        InputDeclarationImpl other = (InputDeclarationImpl) obj;
        if (inputs.size() != other.getInputs().size()) return false;

        if (!inputs.containsAll(other.getInputs())) return false;
        return other.getInputs().containsAll(inputs);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("in()");

        for (Input input : inputs) {
            str.append(".").append(input.toString());
        }

        return str.toString();
    }
}
