package de.fraunhofer.iem.secucheck.InternalFluentTQL;

import org.junit.Test;

public class MethodTest {
    @Test
    public void test1() {
        System.out.println(DummySpecification.methodTest1.equals(DummySpecification.methodTest2));
        System.out.println(DummySpecification.methodTest2.equals(DummySpecification.methodTest1));
        System.out.println(DummySpecification.methodTest1.hashCode());
        System.out.println(DummySpecification.methodTest2.hashCode());
        System.out.println(DummySpecification.methodTest1.toString());
        System.out.println(DummySpecification.methodTest2.toString());

        System.out.println(DummySpecification.methodTest1.equals(DummySpecification.methodTest3));
        System.out.println(DummySpecification.methodTest3.equals(DummySpecification.methodTest1));
        System.out.println(DummySpecification.methodTest3.hashCode());
        System.out.println(DummySpecification.methodTest3.toString());
    }
}
