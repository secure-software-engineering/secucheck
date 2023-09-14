package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.InputOutput.Return;

/**
 * Implementation of {@link Return} value
 *
 * @author Ranjith Krishnamurthy
 */
class ReturnImpl implements Return {
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + "return".hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return true;
    }

    @Override
    public String toString() {
        return "returnValue()";
    }
}
