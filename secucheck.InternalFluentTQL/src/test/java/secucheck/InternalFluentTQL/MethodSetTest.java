package secucheck.InternalFluentTQL;

import secucheck.InternalFluentTQL.dsl.MethodSet;
import org.junit.Test;

public class MethodSetTest {
    @Test
    public void test1() {
        MethodSet methodSet1 = new MethodSet("dummy1");
        MethodSet methodSet2 = new MethodSet("dummy2");

        System.out.println(methodSet1.equals(methodSet2));
        System.out.println(methodSet2.equals(methodSet1));
        System.out.println(methodSet1.hashCode());
        System.out.println(methodSet2.hashCode());
        System.out.println(methodSet1.toString());
        System.out.println(methodSet2.toString());
    }

    @Test
    public void test2() {
        MethodSet methodSet1 = new MethodSet("dummy1");
        MethodSet methodSet2 = new MethodSet("dummy2");

        methodSet1.addMethod(DummySpecification.method5);
        methodSet1.addMethod(DummySpecification.method1);
        methodSet1.addMethod(DummySpecification.method4);
        methodSet1.addMethod(DummySpecification.method3);
        methodSet1.addMethod(DummySpecification.method5);
        methodSet1.addMethod(DummySpecification.method2);

        methodSet2.addMethod(DummySpecification.method1);
        methodSet2.addMethod(DummySpecification.method2);
        methodSet2.addMethod(DummySpecification.method3);
        methodSet2.addMethod(DummySpecification.method4);
        methodSet2.addMethod(DummySpecification.method5);

        System.out.println(methodSet1.equals(methodSet2));
        System.out.println(methodSet2.equals(methodSet1));
        System.out.println(methodSet1.hashCode());
        System.out.println(methodSet2.hashCode());
        System.out.println(methodSet1.toString());
        System.out.println(methodSet2.toString());
    }

    @Test
    public void test3() {
        MethodSet methodSet1 = new MethodSet("dummy1");
        MethodSet methodSet2 = new MethodSet("dummy2");

        methodSet1.addMethod(DummySpecification.method1);
        methodSet1.addMethod(DummySpecification.method2);
        methodSet1.addMethod(DummySpecification.method3);
        methodSet1.addMethod(DummySpecification.method4);
        methodSet1.addMethod(DummySpecification.method5);

        methodSet2.addMethod(DummySpecification.method6);
        methodSet2.addMethod(DummySpecification.method1);
        methodSet2.addMethod(DummySpecification.method2);
        methodSet2.addMethod(DummySpecification.method3);
        methodSet2.addMethod(DummySpecification.method4);

        System.out.println(methodSet1.equals(methodSet2));
        System.out.println(methodSet2.equals(methodSet1));
        System.out.println(methodSet1.hashCode());
        System.out.println(methodSet2.hashCode());
        System.out.println(methodSet1.toString());
        System.out.println(methodSet2.toString());
    }
}
