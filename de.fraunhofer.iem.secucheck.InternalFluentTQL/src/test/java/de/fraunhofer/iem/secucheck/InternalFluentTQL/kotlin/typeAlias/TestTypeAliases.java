package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.typeAlias;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodConfigurator;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.kotlin.kotlinTypeAlias.TypeAliases;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.methodSignature.MethodSignatureBuilder;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.MethodSignature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTypeAliases {
    public static TypeAliases validTypeAliases = new TypeAliases();

    static {
        validTypeAliases.add("ArrayList", "java.util.ArrayList");
        validTypeAliases.add("LinkedHashMap", "java.util.LinkedHashMap");
        validTypeAliases.add("HashMap", "java.util.HashMap");
        validTypeAliases.add("LinkedHashSet", "java.util.LinkedHashSet");
        validTypeAliases.add("HashSet", "java.util.HashSet");

        validTypeAliases.add("kotlin.collections.ArrayList", "java.util.ArrayList");
        validTypeAliases.add("kotlin.collections.LinkedHashMap", "java.util.LinkedHashMap");
        validTypeAliases.add("kotlin.collections.HashMap", "java.util.HashMap");
        validTypeAliases.add("kotlin.collections.LinkedHashSet", "java.util.LinkedHashSet");
        validTypeAliases.add("kotlin.collections.HashSet", "java.util.HashSet");

        validTypeAliases.add("ArrayList?", "java.util.ArrayList");
        validTypeAliases.add("LinkedHashMap?", "java.util.LinkedHashMap");
        validTypeAliases.add("HashMap?", "java.util.HashMap");
        validTypeAliases.add("LinkedHashSet?", "java.util.LinkedHashSet");
        validTypeAliases.add("HashSet?", "java.util.HashSet");

        validTypeAliases.add("kotlin.collections.ArrayList?", "java.util.ArrayList");
        validTypeAliases.add("kotlin.collections.LinkedHashMap?", "java.util.LinkedHashMap");
        validTypeAliases.add("kotlin.collections.HashMap?", "java.util.HashMap");
        validTypeAliases.add("kotlin.collections.LinkedHashSet?", "java.util.LinkedHashSet");
        validTypeAliases.add("kotlin.collections.HashSet?", "java.util.HashSet");
    }

    @Test
    public void test1() {
        MethodSignature methodSignature1 = new MethodSignatureBuilder(validTypeAliases)
                .atClass("de.fraunhofer.iem.MainKt")
                .returns("ArrayList")
                .named("dummy")
                .parameter("LinkedHashMap")
                .parameter("kotlin.collections.HashMap")
                .parameter("HashSet")
                .configure();

        String sig = "de.fraunhofer.iem.MainKt: ArrayList dummy(LinkedHashMap, kotlin.collections.HashMap, HashSet)";

        MethodSelector methodSelector = new MethodSelector(methodSignature1);
        Method method = new MethodConfigurator(methodSignature1, validTypeAliases)
                .in().param(0)
                .configure();

        MethodSelector methodSelector1 = new MethodSelector(sig, validTypeAliases);
        Method method1 = new MethodConfigurator(sig, validTypeAliases)
                .in().param(0)
                .configure();

        assertEquals("de.fraunhofer.iem.MainKt: java.util.ArrayList dummy(java.util.LinkedHashMap,java.util.HashMap,java.util.HashSet)",
                methodSignature1.getCompleteMethodSignature());
        assertEquals("de.fraunhofer.iem.MainKt: java.util.ArrayList dummy(java.util.LinkedHashMap,java.util.HashMap,java.util.HashSet)",
                methodSelector.getSignature());
        assertEquals("de.fraunhofer.iem.MainKt: java.util.ArrayList dummy(java.util.LinkedHashMap,java.util.HashMap,java.util.HashSet)",
                method.getSignature());
        assertEquals("de.fraunhofer.iem.MainKt: java.util.ArrayList dummy(java.util.LinkedHashMap,java.util.HashMap,java.util.HashSet)",
                methodSelector1.getSignature());
        assertEquals("de.fraunhofer.iem.MainKt: java.util.ArrayList dummy(java.util.LinkedHashMap,java.util.HashMap,java.util.HashSet)",
                method1.getSignature());

        System.out.println(methodSignature1.getCompleteMethodSignature());
        System.out.println(methodSelector.getSignature());
        System.out.println(method.getSignature());
        System.out.println(methodSelector1.getSignature());
        System.out.println(method1.getSignature());

        System.out.println(methodSignature1);
        System.out.println(methodSelector.getMethodSignature());
        System.out.println(method.getMethodSignature());
        System.out.println(methodSelector1.getMethodSignature());
        System.out.println(method1.getMethodSignature());
    }
}
