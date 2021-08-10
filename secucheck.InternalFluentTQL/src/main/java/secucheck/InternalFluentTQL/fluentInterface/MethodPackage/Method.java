package secucheck.InternalFluentTQL.fluentInterface.MethodPackage;

import secucheck.InternalFluentTQL.dsl.MethodSet;
import secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import secucheck.InternalFluentTQL.fluentInterface.TaintFlowPackage.FlowParticipant;

/**
 * Interface for Method
 *
 */
public interface Method extends FlowParticipant {

    /**
     * Returns the Method signature
     *
     * @return Method signature
     */
    String getSignature();

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
