package secucheck.InternalFluentTQL;

import secucheck.InternalFluentTQL.fluentInterface.InputOutput.Output;
import org.junit.Test;

public class ReturnValueTest {
    @Test
    public void test1() {
        Output output1 = DummySpecification.method6.getOutputDeclaration().getOutputs().get(0);
        Output output2 = DummySpecification.method7.getOutputDeclaration().getOutputs().get(0);


        System.out.println(output1.equals(output2));
        System.out.println(output2.equals(output1));
        System.out.println(output1.hashCode());
        System.out.println(output2.hashCode());
        System.out.println(output1.toString());
        System.out.println(output2.toString());
    }
}
