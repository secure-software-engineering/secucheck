package de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.MethodPackage;

import de.fraunhofer.iem.secucheck.fluenttql.dsl.MethodSet;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput.InputDeclaration;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput.OutputDeclaration;
import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.TaintFlowPackage.FlowParticipant;

/**
 * Interface for Method
 *
 * @author Ranjith Krishnamurthy
 * @author Enri Ozuni
 */
public interface Method extends FlowParticipant {

    /**
     * Returns the Method signature
     *
     * @return Method signature
     */
    String getSignature();
    
    /**
     * Returns the Method signature
     *
     * @return Method signature
     */
    MethodSignature getMethodSignature();

    /**
     * Returns the MethodSet
     *
     * @return MethodSet
     */
    MethodSet getMethodSet();

    /**
     * Returns the InputDeclaration
     *
     * @return InputDeclaration
     */
    InputDeclaration getInputDeclaration();

    /**
     * Returns the OutputDeclaration
     *
     * @return OutputDeclaration
     */
    OutputDeclaration getOutputDeclaration();
}
