package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.*;

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

    @Override
    public String toString() {
        String str = ".in()";

        List<Integer> paramIDs = new ArrayList<>();

        StringBuilder inflowParam = new StringBuilder();
        StringBuilder inflowThisObject = new StringBuilder();

        for (Input input : inputs) {
            if (input instanceof ParameterImpl) {
                ParameterImpl parameter = (ParameterImpl) input;
                paramIDs.add(parameter.getParameterId());
            } else if (input instanceof ThisObjectImpl) {
                inflowThisObject.append(".thisObject()");
            }
        }

        Collections.sort(paramIDs);

        if (paramIDs.size() > 0)
            inflowParam.append(".param(");

        String separator = "";
        for (Integer paramID : paramIDs) {
            inflowParam.append(separator);
            separator = ",";
            inflowParam.append(paramID);
        }

        if (paramIDs.size() > 0)
            inflowParam.append(")");

        str += inflowParam.toString() + inflowThisObject.toString();

        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!this.getClass().equals(obj.getClass()))
            return false;

        return toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}
