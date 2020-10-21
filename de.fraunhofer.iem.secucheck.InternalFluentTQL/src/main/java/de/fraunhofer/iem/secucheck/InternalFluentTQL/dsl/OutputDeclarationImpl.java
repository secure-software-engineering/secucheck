package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.ThisObject;

import java.util.ArrayList;
import java.util.Collections;
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
        Objects.requireNonNull(output, "addOutput() method's argument is null.");

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

    @Override
    public String toString() {
        String str = ".out()";

        List<Integer> paramIDs = new ArrayList<>();

        StringBuilder outflowParam = new StringBuilder();
        StringBuilder outflowThisObject = new StringBuilder();
        StringBuilder outflowReturn = new StringBuilder();

        for (Output output : outputs) {
            if (output instanceof ParameterImpl) {
                ParameterImpl parameter = (ParameterImpl) output;
                paramIDs.add(parameter.getParameterId());
            } else if (output instanceof ThisObjectImpl) {
                outflowThisObject.append(".thisObject()");
            } else if (output instanceof ReturnImpl) {
                outflowReturn.append(".returnValue()");
            }
        }

        if (paramIDs.size() > 0)
            outflowParam.append(".param(");

        Collections.sort(paramIDs);

        String separator = "";
        for (Integer paramID : paramIDs) {
            outflowParam.append(separator);
            separator = ",";
            outflowParam.append(paramID);
        }

        if (paramIDs.size() > 0)
            outflowParam.append(")");

        str += outflowParam.toString() + outflowThisObject.toString() + outflowReturn;

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
