package de.fraunhofer.iem.secucheck.InternalFluentTQL.kotlin.dataTypeTransformer;

import de.fraunhofer.iem.secucheck.InternalFluentTQL.dsl.MethodSelector;
import de.fraunhofer.iem.secucheck.InternalFluentTQL.fluentInterface.MethodPackage.Method;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDataTypeTransformer {
    @Test
    public void test1() {
        Method method1 = new MethodSelector("kotlin.Int: kotlin.Double toDouble()");
        Method method2 = new MethodSelector("de.fraunhofer.iem.TestKt: kotlin.Double toDouble(Double, Int, kotlin.Int?, Dummy)");
        Method method3 = new MethodSelector("de.fraunhofer.iem.MainKt: List propagateList(kotlin.collections.List)");
        Method method4 = new MethodSelector("de.fraunhofer.iem.MainKt: java.util.List propagateList(kotlin.collections.List)");
        Method method5 = new MethodSelector("de.fraunhofer.iem.MainKt: MutableList propagateMutableList(kotlin.collections.MutableList)");

        assertEquals("int: double toDouble()",
                method1.getSignature());

        assertEquals("de.fraunhofer.iem.TestKt: double toDouble(double, int, java.lang.Int, Dummy)",
                method2.getSignature());

        assertEquals("de.fraunhofer.iem.MainKt: java.util.List propagateList(java.util.List)",
                method3.getSignature());

        assertEquals("de.fraunhofer.iem.MainKt: java.util.List propagateList(java.util.List)",
                method4.getSignature());

        assertEquals("de.fraunhofer.iem.MainKt: java.util.List propagateMutableList(java.util.List)",
                method5.getSignature());

        System.out.println(method1.getSignature());
        System.out.println(method2.getSignature());
        System.out.println(method3.getSignature());
        System.out.println(method4.getSignature());
        System.out.println(method5.getSignature());
    }
}
