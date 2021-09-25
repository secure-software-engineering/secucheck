package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.functionTypeInMethodSignature;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.typeAlias.TestTypeAliases;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTypeInMethodSignatureTest {
    MethodSignature methodSignature1 = new MethodSignatureBuilder()
            .atClass("de.fraunhofer.iem.dummy")
            .returns("(Int, Int, _) -> Unit")
            .named("DummyTest")
            .parameter("Int", "String", "(Int, String, String, _) -> String")
            .configure();

    MethodSignature methodSignature2 = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
            .atClass("de.fraunhofer.iem.dummy")
            .returns("(Int, Int, _) -> Unit")
            .named("DummyTest")
            .parameter("ArrayList?", "String", "(Int, _, String, _) -> String")
            .configure();

    MethodSignature methodSignature3 = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
            .atClass("de.fraunhofer.iem.dummy")
            .returns("(Int, _) -> Unit")
            .named("DummyTest")
            .parameter("ArrayList?")
            .parameter("String", "(Int, _, String, _) -> String")
            .configure();

    MethodSignature methodSignature4 = new MethodSignatureBuilder(TestTypeAliases.validTypeAliases)
            .atClass("de.fraunhofer.iem.dummy")
            .returns("Int")
            .named("DummyTest")
            .parameter("ArrayList?")
            .parameter("(Int, Int) -> _)", "() -> String")
            .configure();

    @Test
    public void test1() {
        System.out.println(methodSignature1.getCompleteMethodSignature());
        System.out.println(methodSignature2.getCompleteMethodSignature());
        System.out.println(methodSignature3.getCompleteMethodSignature());
        System.out.println(methodSignature4.getCompleteMethodSignature());

        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(int,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSignature1.getCompleteMethodSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSignature2.getCompleteMethodSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function2 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSignature3.getCompleteMethodSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: int DummyTest(java.util.ArrayList,kotlin.jvm.functions.Function2,kotlin.jvm.functions.Function0)",
                methodSignature4.getCompleteMethodSignature()
        );
    }

    @Test
    public void test2() {
        MethodSelector methodSelector1 = new MethodSelector(methodSignature1);
        MethodSelector methodSelector2 = new MethodSelector(methodSignature2);
        MethodSelector methodSelector3 = new MethodSelector(methodSignature3);
        MethodSelector methodSelector4 = new MethodSelector(methodSignature4);

        System.out.println(methodSelector1.getSignature());
        System.out.println(methodSelector2.getSignature());
        System.out.println(methodSelector3.getSignature());
        System.out.println(methodSelector4.getSignature());

        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(int,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSelector1.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSelector2.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function2 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSelector3.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: int DummyTest(java.util.ArrayList,kotlin.jvm.functions.Function2,kotlin.jvm.functions.Function0)",
                methodSelector4.getSignature()
        );
    }

    @Test
    public void test3() {
        MethodSelector methodSelector1 = new MethodSelector(
                "de.fraunhofer.iem.dummy: (Int , Int, _) -> Unit DummyTest(Int, String, (Int, String, String, _) -> String)"
        );
        MethodSelector methodSelector2 = new MethodSelector(
                "de.fraunhofer.iem.dummy: (Int , Int, _) -> Unit DummyTest(ArrayList?, String, (Int, _, String, _) -> String)",
                TestTypeAliases.validTypeAliases
        );
        MethodSelector methodSelector3 = new MethodSelector(
                "de.fraunhofer.iem.dummy: (Int , _) -> Unit DummyTest(ArrayList?, String, (Int, _, String, _) -> String)",
                TestTypeAliases.validTypeAliases
        );
        MethodSelector methodSelector4 = new MethodSelector(
                "de.fraunhofer.iem.dummy: Int DummyTest(java.util.ArrayList, (Int, Int) -> _, () -> String)",
                TestTypeAliases.validTypeAliases
        );

        System.out.println(methodSelector1.getSignature());
        System.out.println(methodSelector2.getSignature());
        System.out.println(methodSelector3.getSignature());
        System.out.println(methodSelector4.getSignature());

        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(int,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSelector1.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSelector2.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function2 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                methodSelector3.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: int DummyTest(java.util.ArrayList,kotlin.jvm.functions.Function2,kotlin.jvm.functions.Function0)",
                methodSelector4.getSignature()
        );
    }

    @Test
    public void test4() {
        Method method1 = new MethodConfigurator(methodSignature1)
                .in().param(0)
                .out().param(0)
                .configure();
        Method method2 = new MethodConfigurator(methodSignature2)
                .in().param(0)
                .out().param(0)
                .configure();
        Method method3 = new MethodConfigurator(methodSignature3)
                .in().param(0)
                .out().param(0)
                .configure();
        Method method4 = new MethodConfigurator(methodSignature4)
                .in().param(0)
                .out().param(0)
                .configure();

        System.out.println(method1.getSignature());
        System.out.println(method2.getSignature());
        System.out.println(method3.getSignature());
        System.out.println(method4.getSignature());

        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(int,java.lang.String,kotlin.jvm.functions.Function4)",
                method1.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                method2.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function2 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                method3.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: int DummyTest(java.util.ArrayList,kotlin.jvm.functions.Function2,kotlin.jvm.functions.Function0)",
                method4.getSignature()
        );
    }

    @Test
    public void test5() {
        Method method1 = new MethodConfigurator(
                "de.fraunhofer.iem.dummy: (Int , Int, _) -> Unit DummyTest(Int, String, (Int, String, String, _) -> String)"
        ).in().param(0)
                .out().param(0)
                .configure();
        Method method2 = new MethodConfigurator(
                "de.fraunhofer.iem.dummy: (Int , Int, _) -> Unit DummyTest(ArrayList?, String, (Int, _, String, _) -> String)",
                TestTypeAliases.validTypeAliases
        ).in().param(0)
                .out().param(0)
                .configure();
        Method method3 = new MethodConfigurator(
                "de.fraunhofer.iem.dummy: (Int , _) -> Unit DummyTest(ArrayList?, String, (Int, _, String, _) -> String)",
                TestTypeAliases.validTypeAliases
        ).in().param(0)
                .out().param(0)
                .configure();
        Method method4 = new MethodConfigurator(
                "de.fraunhofer.iem.dummy: Int DummyTest(java.util.ArrayList, (Int, Int) -> _, () -> String)",
                TestTypeAliases.validTypeAliases
        ).in().param(0)
                .out().param(0)
                .configure();

        System.out.println(method1.getSignature());
        System.out.println(method2.getSignature());
        System.out.println(method3.getSignature());
        System.out.println(method4.getSignature());

        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(int,java.lang.String,kotlin.jvm.functions.Function4)",
                method1.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function3 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                method2.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: kotlin.jvm.functions.Function2 DummyTest(java.util.ArrayList,java.lang.String,kotlin.jvm.functions.Function4)",
                method3.getSignature()
        );
        assertEquals(
                "de.fraunhofer.iem.dummy: int DummyTest(java.util.ArrayList,kotlin.jvm.functions.Function2,kotlin.jvm.functions.Function0)",
                method4.getSignature()
        );
    }
}
