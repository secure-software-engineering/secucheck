package de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

import java.util.ArrayList;
import java.util.List;

/**
 * This class combines the multiple Method into one (equivalent to OR operator).
 *
 * @author Ranjith Krishnamurthy
 */
public class MethodSet implements FlowParticipant {
    private final String methodSetName;
    private final List<Method> methods = new ArrayList<Method>();

    /**
     * Constructors that sets the name for the MethodSet.
     *
     * @param methodSetName MethodSet name
     */
    public MethodSet(String methodSetName) {
        this.methodSetName = methodSetName;
    }

    /**
     * This method adds the Method to the MethodSet
     *
     * @param method Method
     * @return MethodSet
     */
    public MethodSet addMethod(Method method) {
        methods.add(method);
        ((MethodSelector) method).setMethodSet(this);
        return this;
    }

    /**
     * This method returns the name of the MethodSet
     *
     * @return MethodSet name
     */
    public String getName() {
        return methodSetName;
    }

    /**
     * This method returns the List of all the methods in this MethodSet.
     *
     * @return List of all the Methods.
     */
    public List<Method> getMethods() {
        return methods;
    }
}
