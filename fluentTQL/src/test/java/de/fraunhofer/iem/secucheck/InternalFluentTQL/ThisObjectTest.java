package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Input;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import org.junit.Test;

public class ThisObjectTest {
    @Test
    public void test1() {
        Output output = DummySpecification.method1.getOutputDeclaration().getOutputs().get(0);
        Input input = DummySpecification.method2.getInputDeclaration().getInputs().get(0);


        System.out.println(output.equals(input));
        System.out.println(input.equals(output));
        System.out.println(output.hashCode());
        System.out.println(input.hashCode());
        System.out.println(output.toString());
        System.out.println(input.toString());
    }
}
