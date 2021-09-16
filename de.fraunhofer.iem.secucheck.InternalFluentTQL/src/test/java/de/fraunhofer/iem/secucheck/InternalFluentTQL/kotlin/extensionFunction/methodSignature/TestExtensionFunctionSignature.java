package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.extensionFunction.methodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestExtensionFunctionSignature {
    MethodSignature methodSignature1 = new MethodSignatureBuilder()
            .atClass("de.fraunhofer.iem.OuterClass")
            .extensionFunction("Int")
            .returns("Unit")
            .named("print")
            .configure();

    MethodSignature methodSignature2 = new MethodSignatureBuilder()
            .topLevelMember("Example", "de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections")
            .extensionFunction("List")
            .returns("kotlin.Int")
            .named("customizedMax")
            .configure();

    MethodSignature methodSignature3 = new MethodSignatureBuilder()
            .topLevelMember("Example", "de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections")
            .extensionFunction("kotlin.collections.List")
            .returns("kotlin.Unit")
            .named("customizedSwap")
            .parameter("Int")
            .parameter("Int")
            .configure();

    @Test
    public void test1() {
        assertEquals("de.fraunhofer.iem.OuterClass: void print(int)",
                methodSignature1.getCompleteMethodSignature());
        assertEquals("de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections.ExampleKt: int customizedMax(java.util.List)",
                methodSignature2.getCompleteMethodSignature());
        assertEquals("de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections.ExampleKt: void customizedSwap(java.util.List, int, int)",
                methodSignature3.getCompleteMethodSignature());

        System.out.println(methodSignature1.getCompleteMethodSignature());
        System.out.println(methodSignature2.getCompleteMethodSignature());
        System.out.println(methodSignature3.getCompleteMethodSignature());

        System.out.println(methodSignature1);
        System.out.println(methodSignature2);
        System.out.println(methodSignature3);
    }
}
