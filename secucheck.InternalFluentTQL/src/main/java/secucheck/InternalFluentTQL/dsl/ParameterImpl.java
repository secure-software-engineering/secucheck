package secucheck.InternalFluentTQL.dsl;

import secucheck.InternalFluentTQL.fluentInterface.InputOutput.Parameter;

/**
 * Implementaion of Parameter
 *
 */
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.hashCode(parameterId);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ParameterImpl other = (ParameterImpl) obj;
        return parameterId == other.getParameterId();
    }

    @Override
    public String toString() {
        return "param(" + parameterId + ")";
    }
}
