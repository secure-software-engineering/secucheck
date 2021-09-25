package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.extensionFunction.qualifiedThis;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.FluentTQLRuntimeException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.exception.runTimeException.InvalidQualifiedThisException;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.QualifiedThis;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestExtensionFunctionQualifiedThis {
    MethodSignature methodSignature1 = new MethodSignatureBuilder()
            .atClass("de.fraunhofer.iem.OuterClass")
            .extensionFunction("Int")
            .returns("Unit")
            .named("print")
            .configure();

    Method validMethod1 = new MethodConfigurator(methodSignature1)
            .in().thisObject()
            .out().thisObject()
            .configure();

    Method inValidMethod1 = new MethodConfigurator(methodSignature1)
            .in().thisObject(QualifiedThis.DISPATCH_RECEIVER)
            .out().thisObject()
            .configure();

    MethodSignature methodSignature2 = new MethodSignatureBuilder()
            .topLevelMember("Example", "de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections")
            .extensionFunction("List")
            .returns("kotlin.Int")
            .named("customizedMax")
            .configure();

    Method validMethod2 = new MethodConfigurator(methodSignature2)
            .in().thisObject().param(0)
            .out().thisObject(QualifiedThis.EXTENSION_RECEIVER).param(0)
            .configure();

    MethodSignature methodSignature3 = new MethodSignatureBuilder()
            .topLevelMember("Example", "de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections")
            .extensionFunction("kotlin.collections.List")
            .returns("kotlin.Unit")
            .named("customizedSwap")
            .parameter("Int")
            .parameter("Int")
            .configure();

    Method validMethod3 = new MethodConfigurator(methodSignature3)
            .in().param(0).param(1)
            .out().thisObject(QualifiedThis.EXTENSION_RECEIVER).param(1)
            .configure();

    @Test
    public void test1() {
        assertEquals(("Method(\"de.fraunhofer.iem.OuterClass: void print(int)\")\n" +
                        "      .in().param(0)\n" +
                        "      .out().param(0)").replaceAll("\\s+", ""),
                validMethod1.toString().replaceAll("\\s+", ""));
        assertEquals(("Method(\"de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections.ExampleKt: int customizedMax(java.util.List)\")\n" +
                        "      .in().param(0).param(1)\n" +
                        "      .out().param(0).param(1)").replaceAll("\\s+", ""),
                validMethod2.toString().replaceAll("\\s+", ""));
        assertEquals(("Method(\"de.fraunhofer.iem.uniqueToKotlin.extensions.extensionFunctions.extenstionToCollections.ExampleKt: void customizedSwap(java.util.List,int,int)\")\n" +
                        "      .in().param(1).param(2)\n" +
                        "      .out().param(0).param(2)").replaceAll("\\s+", ""),
                validMethod3.toString().replaceAll("\\s+", ""));
        assertEquals(("Method(\"de.fraunhofer.iem.OuterClass: void print(int)\")\n" +
                        "      .in().thisObject()\n" +
                        "      .out().param(0)").replaceAll("\\s+", ""),
                inValidMethod1.toString().replaceAll("\\s+", ""));

        System.out.println(validMethod1);
        System.out.println(validMethod2);
        System.out.println(validMethod3);
        System.out.println(inValidMethod1);
    }

    @Test(expected = InvalidQualifiedThisException.class)
    public void test2() {
        Method method = new MethodConfigurator(methodSignature2)
                .in().thisObject(QualifiedThis.DISPATCH_RECEIVER)
                .out().param(0)
                .configure();
    }

    @Test
    public void test3() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("dummy")
                .returns("Int")
                .named("testing")
                .parameter("Int")
                .parameter("Int")
                .parameter("Int")
                .configure();

        Method method1 = new MethodConfigurator(methodSignature)
                .in().thisObject(QualifiedThis.DISPATCH_RECEIVER)
                .out().param(0)
                .configure();

        Method method2 = new MethodConfigurator(methodSignature)
                .in().thisObject()
                .out().param(0)
                .configure();

        System.out.println(method1);
        System.out.println(method2);

        assertEquals(("Method(\"dummy: int testing(int,int,int)\")\n" +
                        "      .in().thisObject()\n" +
                        "      .out().param(0)").replaceAll("\\s+", ""),
                method1.toString().replaceAll("\\s+", ""));
        assertEquals(("Method(\"dummy: int testing(int,int,int)\")\n" +
                        "      .in().thisObject()\n" +
                        "      .out().param(0)").replaceAll("\\s+", ""),
                method2.toString().replaceAll("\\s+", ""));

    }

    @Test(expected = InvalidQualifiedThisException.class)
    public void test4() {
        MethodSignature methodSignature = new MethodSignatureBuilder()
                .atClass("dummy")
                .returns("Int")
                .named("testing")
                .parameter("Int")
                .parameter("Int")
                .parameter("Int")
                .configure();

        Method method1 = new MethodConfigurator(methodSignature)
                .in().thisObject(QualifiedThis.EXTENSION_RECEIVER)
                .out().param(0)
                .configure();
    }
}
