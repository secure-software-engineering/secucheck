package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput.ThisObject;

/**
 * Implementation of {@link ThisObject}
 *
 * @author Ranjith Krishnamurthy
 */
class ThisObjectImpl implements ThisObject {
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + "this".hashCode();
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
        return "thisObject()";
    }
}
