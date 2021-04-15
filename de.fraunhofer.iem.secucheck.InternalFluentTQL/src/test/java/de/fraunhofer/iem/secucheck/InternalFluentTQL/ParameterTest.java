package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import org.junit.Test;

public class ParameterTest {
    @Test
    public void test1() {
        Output param1 = DummySpecification.method3.getOutputDeclaration().getOutputs().get(0);
        Input param2 = DummySpecification.method4.getInputDeclaration().getInputs().get(0);


        System.out.println(param1.equals(param2));
        System.out.println(param2.equals(param1));
        System.out.println(param1.hashCode());
        System.out.println(param2.hashCode());
        System.out.println(param1.toString());
        System.out.println(param2.toString());

        Input param3 = DummySpecification.method5.getInputDeclaration().getInputs().get(0);

        System.out.println(param1.equals(param3));
        System.out.println(param3.equals(param1));
        System.out.println(param3.hashCode());
        System.out.println(param3.toString());
    }
}
