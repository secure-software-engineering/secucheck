package de.fraunhofer.iem.secucheck.InternalFluentTQL.MethodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestToString {
    @Test
    public void test1() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .parameter("java.lang.String")
                .parameter("java.lang.String")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, java.lang.String)>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, java.lang.String)",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test
    public void test2() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .parameter("java.lang.String")
                .parameter("_")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, _)>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize(java.lang.String, _)",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }

    @Test
    public void test3() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("de.fraunhofer.iem.Dummy")
                .returns("java.lang.String")
                .named("sanitize")
                .configure();

        assertEquals("MethodSignature<de.fraunhofer.iem.Dummy: java.lang.String sanitize()>",
                methodSignature.toString());
        assertEquals("de.fraunhofer.iem.Dummy: java.lang.String sanitize()",
                methodSignature.getCompleteMethodSignature());

        System.out.println(methodSignature);
        System.out.println(methodSignature.getCompleteMethodSignature());
    }
}
