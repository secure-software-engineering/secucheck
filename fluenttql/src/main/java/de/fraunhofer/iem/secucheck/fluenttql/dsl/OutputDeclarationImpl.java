package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.Output;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.ThisObject;

import java.util.*;

/**
 * Implementation of {@link OutputDeclaration}
 *
 * @author Ranjith Krishnamurthy
 */
class OutputDeclarationImpl implements OutputDeclaration {
    private final Set<Output> outputs = new LinkedHashSet<>();

    private final List<Output> outputsAsList = new ArrayList<>();

    public List<Output> getOutputs() {
        outputsAsList.clear();
        outputsAsList.addAll(outputs);
        return outputsAsList;
    }

    public Set<Output> getOutputsAsSet() {
        return outputs;
    }

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + "out".hashCode();
        result = prime * result + outputs.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        OutputDeclarationImpl other = (OutputDeclarationImpl) obj;
        if (outputs.size() != other.getOutputs().size()) return false;

        if (!outputs.containsAll(other.getOutputs())) return false;
        return other.getOutputs().containsAll(outputs);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("out()");

        for (Output output : outputs) {
            str.append(".").append(output.toString());
        }

        return str.toString();
    }
}
