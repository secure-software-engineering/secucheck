package secucheck.InternalFluentTQL;

import secucheck.InternalFluentTQL.fluentInterface.InputOutput.InputDeclaration;
import secucheck.InternalFluentTQL.fluentInterface.InputOutput.OutputDeclaration;
import org.junit.Test;

public class OutputDecTest {
    @Test
    public void test1() {
        OutputDeclaration output1 = DummySpecification.outputDeclarationTest1.getOutputDeclaration();
        OutputDeclaration output2 = DummySpecification.outputDeclarationTest2.getOutputDeclaration();


        System.out.println(output1.equals(output2));
        System.out.println(output2.equals(output1));
        System.out.println(output1.hashCode());
        System.out.println(output2.hashCode());
        System.out.println(output1.toString());
        System.out.println(output2.toString());

        OutputDeclaration output3 = DummySpecification.outputDeclarationTest3.getOutputDeclaration();

        System.out.println(output1.equals(output3));
        System.out.println(output3.equals(output1));
        System.out.println(output3.hashCode());
        System.out.println(output3.toString());
    }

    @Test
    public void test2() {
        InputDeclaration input = DummySpecification.inputDeclarationTest1.getInputDeclaration();
        OutputDeclaration output = DummySpecification.outputDeclarationTest4.getOutputDeclaration();


        System.out.println(input.equals(output));
        System.out.println(output.equals(input));
        System.out.println(input.hashCode());
        System.out.println(output.hashCode());
        System.out.println(input.toString());
        System.out.println(output.toString());
    }
}
