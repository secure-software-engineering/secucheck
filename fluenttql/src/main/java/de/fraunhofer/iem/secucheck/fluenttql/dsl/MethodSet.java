package de.fraunhofer.iem.secucheck.fluenttql.dsl;

import de.fraunhofer.iem.secucheck.fluenttql.interfaces.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.fluenttql.interfaces.TaintFlowPackage.FlowParticipant;

import java.util.*;

/**
 * MethodSet can containt list of {@link Method}
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSet implements FlowParticipant {
    private final String methodSetName;
    private final Set<Method> methods = new LinkedHashSet<>();

    private final List<Method> methodsAsList = new ArrayList<Method>();

    public MethodSet(String methodSetName) {
        this.methodSetName = methodSetName;
    }

    public MethodSet addMethod(Method method) {
        Objects.requireNonNull(method, "addMethod() method's argument is null.");

        methods.add(method);
        return this;
    }

    public String getName() {
        return methodSetName;
    }

    public List<Method> getMethods() {
        methodsAsList.clear();
        methodsAsList.addAll(methods);
        return methodsAsList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + methods.hashCode();

        // result = prime * result + ((methodSetName == null) ? 0 : methodSetName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        MethodSet other = (MethodSet) obj;
        /*if (methodSetName == null) {
            if (other.getName() != null)
                return false;
        } else if (!methodSetName.equals(other.getName()))
            return false;*/

        if (methods.size() != other.getMethods().size()) return false;

        if (!methods.containsAll(other.getMethods())) return false;
        return other.getMethods().containsAll(methods);
    }

    @Override
    public String toString() {
        return "MethodSet(\"" + methodSetName + "\")[" + methods.size() + "]";
    }
}
