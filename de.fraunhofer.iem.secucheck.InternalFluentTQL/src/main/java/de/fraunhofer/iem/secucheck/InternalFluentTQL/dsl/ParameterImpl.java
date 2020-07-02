package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Parameter;

class ParameterImpl implements Parameter {
    private int parameterId;

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterID) {
        this.parameterId = parameterID;
    }

    public ParameterImpl(int parameterId) {
        this.parameterId = parameterId;
    }
}
