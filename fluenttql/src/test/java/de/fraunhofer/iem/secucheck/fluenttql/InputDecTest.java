package de.fraunhofer.iem.secucheck.fluenttql;

import de.fraunhofer.iem.secucheck.fluenttql.fluentInterface.InputOutput.InputDeclaration;
import org.junit.Test;

public class InputDecTest {
    @Test
    public void test1() {
        InputDeclaration input1 = DummySpecification.inputDeclarationTest1.getInputDeclaration();
        InputDeclaration input2 = DummySpecification.inputDeclarationTest2.getInputDeclaration();


        System.out.println(input1.equals(input2));
        System.out.println(input2.equals(input1));
        System.out.println(input1.hashCode());
        System.out.println(input2.hashCode());
        System.out.println(input1.toString());
        System.out.println(input2.toString());

        InputDeclaration input3 = DummySpecification.inputDeclarationTest3.getInputDeclaration();

        System.out.println(input1.equals(input3));
        System.out.println(input3.equals(input1));
        System.out.println(input3.hashCode());
        System.out.println(input3.toString());
    }
}
