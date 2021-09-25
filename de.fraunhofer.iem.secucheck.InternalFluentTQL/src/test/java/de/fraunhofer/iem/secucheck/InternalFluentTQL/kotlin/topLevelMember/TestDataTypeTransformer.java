package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.topLevelMember;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDataTypeTransformer {
    @Test
    public void test1() {
        MethodSignature methodSignature1 = new MethodSignatureBuilder()
                .topLevelMember("Test", "de.fraunhofer.iem")
                .returns("kotlin.Double")
                .named("toDouble")
                .parameter("Double")
                .parameter("Int")
                .parameter("kotlin.Int?")
                .parameter("Dummy")
                .configure();

        MethodSignature methodSignature2 = new MethodSignatureBuilder()
                .topLevelMember("Main", "de.fraunhofer.iem")
                .returns("List")
                .named("propagateList")
                .parameter("kotlin.collections.List")
                .configure();

        MethodSignature methodSignature3 = new MethodSignatureBuilder()
                .topLevelMember("Main", "de.fraunhofer.iem")
                .returns("java.util.List")
                .named("propagateList")
                .parameter("kotlin.collections.List")
                .configure();

        MethodSignature methodSignature4 = new MethodSignatureBuilder()
                .topLevelMember("Main", "de.fraunhofer.iem")
                .returns("MutableList")
                .named("propagateMutableList")
                .parameter("kotlin.collections.MutableList")
                .configure();

        Method method1 = new MethodSelector(methodSignature1);
        Method method2 = new MethodSelector(methodSignature2);
        Method method3 = new MethodSelector(methodSignature3);
        Method method4 = new MethodSelector(methodSignature4);

        assertEquals("de.fraunhofer.iem.TestKt: double toDouble(double,int,java.lang.Int,Dummy)",
                method1.getSignature());

        assertEquals("de.fraunhofer.iem.MainKt: java.util.List propagateList(java.util.List)",
                method2.getSignature());

        assertEquals("de.fraunhofer.iem.MainKt: java.util.List propagateList(java.util.List)",
                method3.getSignature());

        assertEquals("de.fraunhofer.iem.MainKt: java.util.List propagateMutableList(java.util.List)",
                method4.getSignature());

        System.out.println(method1.getSignature());
        System.out.println(method2.getSignature());
        System.out.println(method3.getSignature());
        System.out.println(method4.getSignature());

        System.out.println(method1.getMethodSignature());
        System.out.println(method2.getMethodSignature());
        System.out.println(method3.getMethodSignature());
        System.out.println(method4.getMethodSignature());
    }
}
